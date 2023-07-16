package tri.le.migrate.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

interface RecentContactRepo : CrudRepository<RecentContact, Long> {

  @Query("SELECT * FROM recent_contact where contact_id > (RAND() * 3000000) LIMIT 1", nativeQuery = true)
  fun findRandom(): RecentContact
}

@Entity
@IdClass(RecentContactId::class)
data class RecentContact(
  @Id
  @Column(name = "profile_id")
  val profileId: String,

  @Id
  @Column(name = "contact_id")
  val contactId: Long,

  val createdDate: LocalDateTime = LocalDateTime.now(),
  val modifiedDate: LocalDateTime = LocalDateTime.now()
)

data class RecentContactId(
  val profileId: String = "",
  val contactId: Long = 0
) : Serializable
