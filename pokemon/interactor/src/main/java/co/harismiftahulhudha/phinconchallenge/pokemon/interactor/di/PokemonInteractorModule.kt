package co.harismiftahulhudha.phinconchallenge.pokemon.interactor.di

import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepository
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class PokemonInteractorModule {
    @Provides
    @ViewModelScoped
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonListUseCase {
        return GetPokemonListUseCaseImpl(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPokemonDetailUseCase(repository: PokemonRepository): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCaseImpl(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideRequestCatchingPokemonUseCase(repository: PokemonRepository): RequestCatchingPokemonUseCase {
        return RequestCatchingPokemonUseCaseImpl(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCatchPokemonUseCase(repository: PokemonRepository): CatchPokemonUseCase {
        return CatchPokemonUseCaseImpl(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideReleasePokemonUseCase(repository: PokemonRepository): ReleasePokemonUseCase {
        return ReleasePokemonUseCaseImpl(repository = repository)
    }

    @Provides
    @ViewModelScoped
    fun provideRenamePokemonUseCase(repository: PokemonRepository): RenamePokemonUseCase {
        return RenamePokemonUseCaseImpl(repository = repository)
    }
}