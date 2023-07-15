package tri.le.migrate.service

import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import tri.le.migrate.repository.Contact
import tri.le.migrate.repository.ContactRepo
import tri.le.migrate.util.Log
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger
import javax.transaction.Transactional


interface FakeDataService {
  fun fakeContacts(number: Int)
}

@Service
class FaceDataServiceImpl(
  val contactRepo: ContactRepo,
  val faker: Faker = Faker(),
  val threadPoolTaskExecutor: Executor
) : FakeDataService, Log {
  override fun fakeContacts(number: Int) {
    val totalRecord = AtomicInteger()
    l.info("Begin generate $number contacts")

    Array(number) { Contact(name = faker.name.nameWithMiddle(), profileId = UUID.randomUUID().toString()) }
      .asSequence()
      .chunked(10000)
      .forEach {
        transactionWrapping {
          contactRepo.saveAll(it)
          l.info("Save ${it.size} contacts successfully. Total record ${totalRecord.addAndGet(it.size)}")
        }
      }

    l.info("Generate $number contacts successfully")
  }

  @Transactional
  fun transactionWrapping(runnable: Runnable) {
    threadPoolTaskExecutor.execute(runnable)
  }
}
