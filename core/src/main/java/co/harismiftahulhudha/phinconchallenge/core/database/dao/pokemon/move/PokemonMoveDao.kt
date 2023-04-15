package co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.move

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveEntity

interface PokemonMoveDao {
    suspend fun insert(data: PokemonMoveEntity): Long
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonMoveEntity?
}