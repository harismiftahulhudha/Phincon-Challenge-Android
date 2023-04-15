package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel

interface GetPokemonListUseCase {
    suspend operator fun invoke(isCaught: Boolean, page: Int): UseCaseResult<List<PokemonModel>>
}