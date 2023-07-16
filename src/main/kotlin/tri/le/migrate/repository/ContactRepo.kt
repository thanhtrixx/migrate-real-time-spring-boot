package tri.le.migrate.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.stream.Stream
import javax.persistence.*


interface ContactRepo : CrudRepository<Contact, Long> {

  fun findAllBy(): Stream<Contact>

  @Query("SELECT * FROM contact ORDER BY RAND() LIMIT 1", nativeQuery = true)
  fun findRandom(): Contact
}


@Entity
class Contact(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  val name: String,

  @Column(name = "profile_id")
  val profileId: String,

  val createdDate: LocalDateTime = LocalDateTime.now(),
  val modifiedDate: LocalDateTime = LocalDateTime.now()
)
