package santannaf.people.core.provider

import java.util.UUID

interface FetchPeopleProvider {
    fun fetchById(id: UUID): UUID?
    fun fetchByTerm(t: String): UUID?
    fun fetchCacheByKey(key: String): UUID?
}
