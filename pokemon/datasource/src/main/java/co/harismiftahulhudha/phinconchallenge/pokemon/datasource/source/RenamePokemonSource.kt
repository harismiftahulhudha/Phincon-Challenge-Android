package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.parser.ApiErrorParser
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.toModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RenamePokemonSource(
    private val apiService: PokemonPersonalApiService,
    private val pokemonDao: PokemonDao,
) {
    private val errorParser by lazy { ApiErrorParser() }

    suspend operator fun invoke(nickName: String, id: Long): Flow<SourceResult<PokemonDetailModel>> = flow {
        try {
            emit(SourceResult.Loading())
            var pokemon = pokemonDao.getDetail(id)
            val index = pokemon.pokemon.indexNickName + 1
            val response = apiService.rename(index = index, nickname = nickName)
            if (response.isSuccessful) {
                pokemonDao.updateCatching(true, response.body()?.data.orBlank(), index, id)
                pokemon = pokemonDao.getDetail(id)
                emit(SourceResult.Success(pokemon.toModel()))
            } else {
                emit(SourceResult.Failure(errorParser(response.code(), response.errorBody())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(SourceResult.Failure(General(e.message.orBlank())))
        }
    }
}