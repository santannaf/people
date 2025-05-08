package santannaf.people.core.usecase

import jakarta.inject.Named
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import santannaf.people.core.entity.PeopleAbstractRequest
import santannaf.people.core.provider.SavePeopleProvider

@Named
class SavePeopleUseCase(
    private val saveProvider: SavePeopleProvider
) {
    fun save(request: PeopleAbstractRequest): UUID {
        val search = request.buildSearch()
        val people = request.buildPeople(search)
        val id = people.id
        val triple = Triple(id, "pessoas:id:$id", id)

        runBlocking {
            awaitAll(
                async(Dispatchers.IO) { saveProvider.saveCache(triple) },
                async(Dispatchers.IO) { saveProvider.saveDatabase(people) }
            )
        }

        return id
    }
}
