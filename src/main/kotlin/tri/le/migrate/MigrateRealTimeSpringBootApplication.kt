package tri.le.migrate

import org.hibernate.annotations.Comment
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import tri.le.migrate.repository.Contact
import tri.le.migrate.repository.ContactRepo
import tri.le.migrate.util.Log
import java.util.UUID

@SpringBootApplication
class MigrateRealTimeSpringBootApplication

fun main(args: Array<String>) {
	runApplication<MigrateRealTimeSpringBootApplication>(*args)
}

@Component
class Runner(
	val contactRepo: ContactRepo
): ApplicationRunner, Log {
	override fun run(args: ApplicationArguments?) {
		l.info("Current size of contact table: ${contactRepo.count()}")

		contactRepo.save(Contact(name = "Tri Le", profileId = UUID.randomUUID().toString()))

		l.info("Current size of contact table: ${contactRepo.count()}")
	}
}