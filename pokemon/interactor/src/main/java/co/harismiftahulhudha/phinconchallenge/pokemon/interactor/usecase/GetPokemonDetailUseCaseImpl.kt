package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepository
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetPokemonDetailUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonDetailUseCase {

    override suspend fun invoke(id: Long): Flow<UseCaseResult<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>>> = flow {
        emitAll(repository.getPokemonDetail(id).map {
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