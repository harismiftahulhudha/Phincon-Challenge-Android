package co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.ability

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityEntity

interface PokemonAbilityDao {
    suspend fun insert(data: PokemonAbilityEntity): Long
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonAbilityEntity?
}