package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepository
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel

class GetPokemonListUseCaseImpl(
    private val repository: PokemonRepository
): GetPokemonListUseCase {

    override suspend fun invoke(isCaught: Boolean, page: Int): UseCaseResult<List<PokemonModel>> {
        return repository.getPokemonList(isCaught, page).fold(
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
    }
}