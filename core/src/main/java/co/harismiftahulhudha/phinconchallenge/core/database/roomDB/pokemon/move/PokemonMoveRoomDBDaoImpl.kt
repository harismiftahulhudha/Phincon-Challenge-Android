package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.move.PokemonMoveDao

class PokemonMoveRoomDBDaoImpl(
    private val dao: PokemonMoveRoomDBDao
): PokemonMoveDao {

    override suspend fun insert(data: PokemonMoveEntity): Long {
        return dao.insert(data = data)
    }

    override suspend fun getByPokemonIdAndName(
        pokemonId: Long,
        name: String
    ): PokemonMoveEntity? {
        return dao.getByPokemonIdAndName(pokemonId = pokemonId, name = name)
    }
}