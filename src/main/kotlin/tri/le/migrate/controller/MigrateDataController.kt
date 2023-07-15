package tri.le.migrate.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tri.le.migrate.service.RecentContactService

@RestController
@RequestMapping("migrate-data")
class MigrateDataController(
  val recentContactService: RecentContactService
) {

  @GetMapping("contact-to-recent-contact")
  fun migrateContactToRecentContact() {
    recentContactService.migrateFromContact()
  }

  @GetMapping("contact-to-recent-contact-one-row")
  fun migrateContactToRecentContactOneRow() {
    recentContactService.migrateFromContactOneRow()
  }

  @GetMapping("count-contact")
  fun countContact(): Int {
    return recentContactService.queryAndCount()
  }
}
