package santannaf.people.core.usecase

import jakarta.inject.Named
import java.util.UUID
import santannaf.people.core.exception.PeopleNotFoundException
import santannaf.people.core.provider.FetchPeopleProvider
import santannaf.people.core.provider.SavePeopleProvider

@Named
class FetchPeopleUseCase(
    private val fetchProvider: FetchPeopleProvider,
    private val saveProvider: SavePeopleProvider
) {
    fun fetchPeopleById(id: UUID): UUID {
        val key = "pessoas:id:$id"
        val result = fetchProvider.fetchCacheByKey(key) ?: fetchProvider.fetchById(id)

        if (result == null) throw PeopleNotFoundException()

        return result
    }

    fun fetchPeopleByTerm(t: String): List<UUID> {
        val key = "cache:findByTerm:$t"
        val result = fetchProvider.fetchCacheByKey(key) ?: fetchProvider.fetchByTerm("%$t%")

        if (result == null) return emptyList()

        saveProvider.saveCache(Triple(UUID.randomUUID(), key, result))
        return listOf(result)
    }
}
