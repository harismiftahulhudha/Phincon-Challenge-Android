package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.parser.ApiErrorParser
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RequestCatchingPokemonSource(
    private val apiService: PokemonPersonalApiService,
) {
    private val errorParser by lazy { ApiErrorParser() }

    suspend operator fun invoke(): Flow<SourceResult<String>> = flow {
        try {
            emit(SourceResult.Loading())
            val response = apiService.catch()
            if (response.isSuccessful) {
                emit(SourceResult.Success(response.body()?.data.orBlank()))
            } else {
                emit(SourceResult.Failure(errorParser(response.code(), response.errorBody())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(SourceResult.Failure(General(e.message.orBlank())))
        }
    }
}