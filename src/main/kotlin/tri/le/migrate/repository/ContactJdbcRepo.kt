package tri.le.migrate.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.util.stream.Stream

@Repository
class ContactJdbcRepo(
  val jdbcTemplate: JdbcTemplate
) {

  private val mapper = ContactRowMapper()

  @Transactional(readOnly = true)
  fun findAllByStream(maxId: Int): Stream<Contact> {
    return jdbcTemplate.queryForStream(
      "SELECT * FROM contact WHERE id <= ?",
      PreparedStatementSetter { it.setInt(1, maxId) }, mapper
    )
  }
}

class ContactRowMapper : RowMapper<Contact> {
  override fun mapRow(rs: ResultSet, rowNum: Int): Contact {
    return Contact(
      id = rs.getLong("id"),
      name = rs.getString("name"),
      profileId = rs.getString("profile_id"),
      createdDate = rs.getTimestamp("created_date").toLocalDateTime(),
      modifiedDate = rs.getTimestamp("modified_date").toLocalDateTime()
    )
  }
}
