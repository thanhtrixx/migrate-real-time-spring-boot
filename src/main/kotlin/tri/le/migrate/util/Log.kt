package tri.le.migrate.util

import org.apache.logging.log4j.kotlin.cachedLoggerOf

interface Log {
  val l
    get() = cachedLoggerOf(this.javaClass)
}
