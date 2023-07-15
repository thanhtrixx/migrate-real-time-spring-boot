package tri.le.migrate.entity

import java.time.LocalDateTime

interface Auditing {

  val createdDate: LocalDateTime
  val modifiedDate: LocalDateTime
}
