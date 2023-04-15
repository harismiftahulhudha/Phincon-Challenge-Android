package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.util.extension.isPrimeNumber
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orZero
import co.harismiftahulhudha.phinconchallenge.core.util.parser.ApiErrorParser
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.toModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReleasePokemonSource(
    private val apiService: PokemonPersonalApiService,
    private val pokemonDao: PokemonDao,
) {
    private val errorParser by lazy { ApiErrorParser() }

    suspend operator fun invoke(id: Long): Flow<SourceResult<PokemonDetailModel>> = flow {
        try {
            emit(SourceResult.Loading())
            val response = apiService.release()
            if (response.isSuccessful) {
                if (response.body()?.data.orZero().isPrimeNumber()) {
                    pokemonDao.updateCatching(false, "", -1, id)
                    emit(SourceResult.Success(pokemonDao.getDetail(id).toModel()))
                } else {
                    emit(SourceResult.Failure(General("Failed to release pokemon")))
                }
            } else {
                emit(SourceResult.Failure(errorParser(response.code(), response.errorBody())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(SourceResult.Failure(General(e.message.orBlank())))
        }
    }
}