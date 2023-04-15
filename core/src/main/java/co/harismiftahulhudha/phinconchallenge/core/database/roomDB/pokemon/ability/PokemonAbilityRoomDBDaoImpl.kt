package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.ability.PokemonAbilityDao

class PokemonAbilityRoomDBDaoImpl(
    private val dao: PokemonAbilityRoomDBDao
): PokemonAbilityDao {

    override suspend fun insert(data: PokemonAbilityEntity): Long {
        return dao.insert(data = data)
    }

    override suspend fun getByPokemonIdAndName(
        pokemonId: Long,
        name: String
    ): PokemonAbilityEntity? {
        return dao.getByPokemonIdAndName(pokemonId = pokemonId, name = name)
    }
}