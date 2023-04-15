package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.stat.PokemonStatDao

class PokemonStatRoomDBDaoImpl(
    private val dao: PokemonStatRoomDBDao
): PokemonStatDao {

    override suspend fun insert(data: PokemonStatEntity): Long {
        return dao.insert(data = data)
    }

    override suspend fun getByPokemonIdAndName(
        pokemonId: Long,
        name: String
    ): PokemonStatEntity? {
        return dao.getByPokemonIdAndName(pokemonId = pokemonId, name = name)
    }
}