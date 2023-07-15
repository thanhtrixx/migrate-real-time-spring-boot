package tri.le.migrate.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class RecentContactJdbcRepo(
  val jdbcTemplate: JdbcTemplate
) {

  fun saveAll(recentContacts: List<RecentContact>) {
    val batchArguments = recentContacts
      .map { arrayOf(it.profileId, it.contactId) }

    jdbcTemplate.batchUpdate("INSERT INTO recent_contact(profile_id, contact_id) values (?, ?)", batchArguments)
  }
}
