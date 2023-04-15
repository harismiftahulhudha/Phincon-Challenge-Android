package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao

class PokemonRoomDBDaoImpl(
    private val dao: PokemonRoomDBDao
): PokemonDao {
    override suspend fun insert(data: PokemonEntity): Long {
        return dao.insert(data)
    }

    override suspend fun getAll(offset: Int): List<PokemonEntity> {
        return dao.getAll(offset)
    }

    override suspend fun getDetail(id: Long): PokemonDetailEntity {
        return dao.getDetail(id)
    }

    override suspend fun getPokemon(id: Long): PokemonEntity? {
        return dao.getPokemon(id)
    }

    override suspend fun getMyPokemon(offset: Int): List<PokemonEntity> {
        return dao.getMyPokemon(offset)
    }

    override suspend fun updateDetail(baseExperience: Int, height: Int, weight: Int, id: Long) {
        return dao.updateDetail(baseExperience = baseExperience, height = height, weight = weight, id = id)
    }

    override suspend fun updateCatching(isCaught: Boolean, nickName: String, indexNickName: Int, id: Long) {
        return dao.updateCatching(isCaught, nickName, indexNickName, id)
    }
}