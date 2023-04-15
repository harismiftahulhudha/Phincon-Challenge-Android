package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface RequestCatchingPokemonUseCase {
    suspend operator fun invoke(): Flow<UseCaseResult<String>>
}