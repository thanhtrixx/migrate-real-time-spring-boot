package tri.le.migrate.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tri.le.migrate.service.FakeDataService

@RestController
@RequestMapping("fake-data")
class FakeDataController(
  val fakeDataService: FakeDataService
) {

  @GetMapping("contact/{number}")
  fun fakeContact(@PathVariable number: Int) {
    fakeDataService.fakeContacts(number)
  }
}
