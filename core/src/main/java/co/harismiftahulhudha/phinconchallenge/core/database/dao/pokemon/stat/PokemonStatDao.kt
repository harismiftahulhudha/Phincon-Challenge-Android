package co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.stat

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatEntity

interface PokemonStatDao {
    suspend fun insert(data: PokemonStatEntity): Long
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonStatEntity?
}