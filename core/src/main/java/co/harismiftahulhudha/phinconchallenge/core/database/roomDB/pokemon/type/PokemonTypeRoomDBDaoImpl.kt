package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.type.PokemonTypeDao

class PokemonTypeRoomDBDaoImpl(
    private val dao: PokemonTypeRoomDBDao
): PokemonTypeDao {

    override suspend fun insert(data: PokemonTypeEntity): Long {
        return dao.insert(data = data)
    }

    override suspend fun getByPokemonIdAndName(
        pokemonId: Long,
        name: String
    ): PokemonTypeEntity? {
        return dao.getByPokemonIdAndName(pokemonId = pokemonId, name = name)
    }
}