package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow

interface ReleasePokemonUseCase {
    suspend operator fun invoke(id: Long): Flow<UseCaseResult<PokemonDetailModel>>
}