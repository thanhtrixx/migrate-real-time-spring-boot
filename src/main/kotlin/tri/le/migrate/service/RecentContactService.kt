package tri.le.migrate.service

import org.springframework.stereotype.Service
import tri.le.migrate.repository.*
import tri.le.migrate.util.Log
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

interface RecentContactService {
  fun migrateFromContact(maxId: Int)

  fun queryAndCount(): Int
}

@Service
class RecentContactServiceImpl(
  val recentContactRepo: RecentContactRepo,
  val contactRepo: ContactRepo,
  val contactJdbcRepo: ContactJdbcRepo,
  val recentContactJdbcRepo: RecentContactJdbcRepo,
  val threadPoolTaskExecutor: Executor
) : RecentContactService, Log {

  override fun migrateFromContact(maxId: Int) {
    val chunkSize = 10000
    var chunk = ArrayList<Contact>(chunkSize)
    var i = 0

    contactJdbcRepo
      .findAllByStream(maxId)
      .forEach {
        if (i < chunkSize) {
          chunk.add(it)
          i++
        } else {
          convertAndSaveRecentContacts(chunk)
          chunk = ArrayList(chunkSize)
          chunk.add(it)
          i = 1
        }
      }

    if (chunk.isNotEmpty()) {
      convertAndSaveRecentContacts(chunk)
    }
    l.info("Done")
  }

  override fun queryAndCount(): Int {
    val counter = AtomicInteger()

    contactJdbcRepo.findAllByStream(Int.MAX_VALUE).forEach { counter.addAndGet(1) }

    l.info("Size of contact table ${counter.get()}")
    return counter.get()
  }

  fun convertAndSaveRecentContacts(contacts: List<Contact>) {
    threadPoolTaskExecutor.execute {
      val recentContacts = contacts.map { RecentContact(contactId = it.id!!, profileId = it.profileId) }
      recentContactJdbcRepo.saveAll(recentContacts)
      l.info { "Save ${recentContacts.size} RecentContacts successfully. Counter ${counter.addAndGet(recentContacts.size)}" }
    }
  }

  val counter = AtomicInteger()
}
