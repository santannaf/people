package santannaf.people.core.dataprovider.repository.postgres

import java.util.UUID
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import santannaf.people.core.entity.People
import santannaf.people.core.provider.FetchPeopleProvider
import santannaf.people.core.provider.SavePeopleProvider

@Repository
class PeoplePostgresProvider(
    private val jdbcClient: JdbcClient
) : SavePeopleProvider, FetchPeopleProvider {
    override fun saveDatabase(people: People) {
        jdbcClient.sql("insert into pessoas (id,apelido,nome,nascimento,stack,busca) values (:id,:nickname,:name,:birthday,:stack,:search)")
            .params(
                mapOf(
                    "id" to people.id,
                    "nickname" to people.nickname,
                    "name" to people.name,
                    "birthday" to people.birthday,
                    "stack" to people.stack,
                    "search" to people.search
                )
            )
            .update()

        return
    }

    override fun saveCache(triple: Triple<UUID, String, UUID>) {
        jdbcClient.sql("insert into cache (key,data) values (:key,:data) on conflict (key) do update set data = :data")
            .params(
                mapOf(
                    "key" to triple.second,
                    "data" to triple.third
                )
            )
            .update()

        return
    }

    override fun fetchById(id: UUID): UUID? {
        return jdbcClient.sql("select id from pessoas where id = ?")
            .param(1, id)
            .query(UUID::class.java)
            .optional()
            .orElse(null)
    }

    override fun fetchByTerm(t: String): UUID? {
        return jdbcClient.sql("select id from pessoas where busca ilike :search limit 1")
            .param("search", t)
            .query(UUID::class.java)
            .optional()
            .orElse(null)
    }

    override fun fetchCacheByKey(key: String): UUID? {
        return jdbcClient.sql("select data from cache where key = :key")
            .param("key", key)
            .query(UUID::class.java)
            .optional()
            .orElse(null)
    }
}
