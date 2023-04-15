package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase

import co.harismiftahulhudha.phinconchallenge.core.util.result.UseCaseResult
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow

interface RenamePokemonUseCase {
    suspend operator fun invoke(nickName: String, id: Long): Flow<UseCaseResult<PokemonDetailModel>>
}