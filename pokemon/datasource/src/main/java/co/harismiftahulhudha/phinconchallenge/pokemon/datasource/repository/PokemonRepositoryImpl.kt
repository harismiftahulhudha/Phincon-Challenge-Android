package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository

import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source.*
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val getPokemonListSource: GetPokemonListSource,
    private val getPokemonDetailSource: GetPokemonDetailSource,
    private val requestCatchingPokemonSource: RequestCatchingPokemonSource,
    private val catchPokemonSource: CatchPokemonSource,
    private val releasePokemonSource: ReleasePokemonSource,
    private val renamePokemonSource: RenamePokemonSource
): PokemonRepository {

    override suspend fun getPokemonList(isCaught: Boolean, page: Int): SourceResult<List<PokemonModel>> {
        return getPokemonListSource(isCaught, page)
    }

    override suspend fun getPokemonDetail(id: Long): Flow<SourceResult<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>>> {
        return getPokemonDetailSource(id)
    }

    override suspend fun requestCatchingPokemon(): Flow<SourceResult<String>> {
        return requestCatchingPokemonSource()
    }

    override suspend fun catchPokemon(nickName: String, id: Long): Flow<SourceResult<PokemonDetailModel>> {
        return catchPokemonSource(nickName, id)
    }

    override suspend fun releasePokemon(id: Long): Flow<SourceResult<PokemonDetailModel>> {
        return releasePokemonSource(id)
    }

    override suspend fun renamePokemon(
        nickName: String,
        id: Long
    ): Flow<SourceResult<PokemonDetailModel>> {
        return renamePokemonSource(nickName, id)
    }
}