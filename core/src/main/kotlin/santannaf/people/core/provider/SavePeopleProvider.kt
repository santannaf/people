package santannaf.people.core.provider

import java.util.UUID
import santannaf.people.core.entity.People

interface SavePeopleProvider {
    fun saveDatabase(people: People)
    fun saveCache(triple: Triple<UUID, String, UUID>)
}
