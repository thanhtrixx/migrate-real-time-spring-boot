package tri.le.migrate.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tri.le.migrate.service.RecentContactService

@RestController
@RequestMapping("migrate-data")
class MigrateDataController(
  val recentContactService: RecentContactService
) {

  @GetMapping("contact-to-recent-contact/{max-id}")
  fun migrateContactToRecentContact(@PathVariable("max-id") maxId: Int) {
    recentContactService.migrateFromContact(maxId)
  }

  @GetMapping("count-contact")
  fun countContact(): Int {
    return recentContactService.queryAndCount()
  }
}
