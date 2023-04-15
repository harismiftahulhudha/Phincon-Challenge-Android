package co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonDetailEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonEntity

interface PokemonDao {
    suspend fun insert(data: PokemonEntity): Long
    suspend fun getAll(offset: Int): List<PokemonEntity>
    suspend fun getDetail(id: Long): PokemonDetailEntity
    suspend fun getPokemon(id: Long): PokemonEntity?
    suspend fun getMyPokemon(offset: Int): List<PokemonEntity>
    suspend fun updateDetail(baseExperience: Int, height: Int, weight: Int, id: Long)
    suspend fun updateCatching(isCaught: Boolean, nickName: String, indexNickName: Int, id: Long)
}