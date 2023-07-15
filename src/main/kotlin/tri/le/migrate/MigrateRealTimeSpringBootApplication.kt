package tri.le.migrate

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import tri.le.migrate.repository.ContactRepo
import tri.le.migrate.repository.RecentContactRepo
import tri.le.migrate.util.Log

@SpringBootApplication
class MigrateRealTimeSpringBootApplication

fun main(args: Array<String>) {
  runApplication<MigrateRealTimeSpringBootApplication>(*args)
}

@Component
class Runner(
  val contactRepo: ContactRepo,
  val recentContactRepo: RecentContactRepo
) : ApplicationRunner, Log {
  override fun run(args: ApplicationArguments?) {
    l.info("Current size of contact table: ${contactRepo.count()}")
    l.info("Current size of recent contact table: ${recentContactRepo.count()}")
  }
}
