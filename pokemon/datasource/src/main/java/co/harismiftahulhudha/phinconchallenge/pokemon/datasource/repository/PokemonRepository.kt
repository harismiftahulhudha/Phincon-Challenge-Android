package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository

import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(isCaught: Boolean, page: Int): SourceResult<List<PokemonModel>>
    suspend fun getPokemonDetail(id: Long): Flow<SourceResult<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>>>
    suspend fun requestCatchingPokemon(): Flow<SourceResult<String>>
    suspend fun catchPokemon(nickName: String, id: Long): Flow<SourceResult<PokemonDetailModel>>
    suspend fun releasePokemon(id: Long): Flow<SourceResult<PokemonDetailModel>>
    suspend fun renamePokemon(nickName: String, id: Long): Flow<SourceResult<PokemonDetailModel>>
}