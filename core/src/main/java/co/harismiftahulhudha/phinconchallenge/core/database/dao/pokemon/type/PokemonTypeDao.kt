package co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.type

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeEntity

interface PokemonTypeDao {
    suspend fun insert(data: PokemonTypeEntity): Long
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonTypeEntity?
}