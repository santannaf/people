package santannaf.people.rest.entrypoint

import java.net.URI
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import santannaf.people.core.usecase.FetchPeopleUseCase
import santannaf.people.core.usecase.SavePeopleUseCase
import santannaf.people.rest.entrypoint.data.request.PeopleRequest

@RestController
class PeopleController(
    private val saveUseCase: SavePeopleUseCase,
    private val fetchUseCase: FetchPeopleUseCase
) {
    @PostMapping(path = ["/pessoas"])
    fun createPeople(@RequestBody payload: PeopleRequest): ResponseEntity<*> {
        val id = saveUseCase.save(payload)
        return ResponseEntity.created(URI.create("/pessoas/$id")).build<String>()
    }

    @GetMapping(path = ["/pessoas/{id}"])
    fun getPeople(@PathVariable id: UUID): ResponseEntity<*> {
        return ResponseEntity.ok(fetchUseCase.fetchPeopleById(id))
    }

    @GetMapping(path = ["/pessoas"])
    fun fetchPeopleByTerm(@RequestParam t: String): ResponseEntity<*> {
        return ResponseEntity.ok(fetchUseCase.fetchPeopleByTerm(t))
    }
}
