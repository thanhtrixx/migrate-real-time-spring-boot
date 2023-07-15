package tri.le.migrate.config

import io.github.serpro69.kfaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
class SpringConfig {

  @Bean
  fun faker() = Faker()

  @Bean(name = ["threadPoolTaskExecutor"])
  fun threadPoolTaskExecutor(): Executor {
    val executor = ThreadPoolTaskExecutor()
    executor.corePoolSize = 25
    executor.maxPoolSize = 50
    executor.setThreadNamePrefix("ThreadPool-")
    executor.initialize()
    return executor
  }
}
