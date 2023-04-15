package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RequestCatchingPokemonUseCaseImpl(
    private val repository: PokemonRepository
) : RequestCatchingPokemonUseCase {

    override suspend fun invoke(): Flow<UseCaseResult<String>> = flow {
        emitAll(repository.requestCatchingPokemon().map {
            it.fold(
                loading = { data ->
                    UseCaseResult.Loading(data)
                },
                success = { data ->
                    UseCaseResult.Success(data)
                },
                failure = { err, data ->
                    UseCaseResult.Failure(err, data)
                }
            )
        })
    }
}