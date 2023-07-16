package tri.le.migrate.service

import org.springframework.stereotype.Service
import tri.le.migrate.repository.*
import tri.le.migrate.util.Log
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

interface RecentContactService {
  fun migrateFromContact()

  fun migrateFromContactOneRow()

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

  override fun migrateFromContact() {
    val chunkSize = 10000
    var chunk = ArrayList<Contact>(chunkSize)
    var i = 0

    contactJdbcRepo
      .findAllByStream()
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

  override fun migrateFromContactOneRow() {
    contactJdbcRepo
      .findAllByStream()
      .forEach {
        convertAndSaveRecentContact(it)
      }
  }

  override fun queryAndCount(): Int {
    val counter = AtomicInteger()

    contactJdbcRepo.findAllByStream().forEach { counter.addAndGet(1) }

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

  fun convertAndSaveRecentContact(contact: Contact) {
    recentContactRepo.save(RecentContact(contactId = contact.id!!, profileId = contact.profileId))
  }

  val counter = AtomicInteger()
}
