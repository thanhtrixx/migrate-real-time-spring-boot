package tri.le.migrate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MigrateRealTimeSpringBootApplication

fun main(args: Array<String>) {
	runApplication<MigrateRealTimeSpringBootApplication>(*args)
}
