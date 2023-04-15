package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.toModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatchPokemonSource(
    private val pokemonDao: PokemonDao,
) {
    suspend operator fun invoke(nickName: String, id: Long): Flow<SourceResult<PokemonDetailModel>> = flow {
        try {
            emit(SourceResult.Loading())
            pokemonDao.updateCatching(true, nickName, -1, id)
            emit(SourceResult.Success(pokemonDao.getDetail(id).toModel()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(SourceResult.Failure(General(e.message.orBlank()), pokemonDao.getDetail(id).toModel()))
        }
    }
}