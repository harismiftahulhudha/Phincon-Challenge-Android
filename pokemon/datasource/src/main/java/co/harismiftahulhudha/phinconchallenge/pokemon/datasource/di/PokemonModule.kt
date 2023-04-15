package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.di

import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.ability.PokemonAbilityDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.move.PokemonMoveDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.stat.PokemonStatDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.type.PokemonTypeDao
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepository
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.repository.PokemonRepositoryImpl
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokemonModule {
    @Provides
    @Singleton
    fun providePokemonApiService(@Named("RetrofitBasePokemon") retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonApiServicePersonal(@Named("RetrofitBasePersonalPokemon") retrofit: Retrofit): PokemonPersonalApiService {
        return retrofit.create(PokemonPersonalApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetPokemonListSource(
        apiService: PokemonApiService,
        @Named("PokemonRoomDBDaoImpl")
        pokemonDao: PokemonDao
    ): GetPokemonListSource {
        return GetPokemonListSource(
            apiService = apiService,
            pokemonDao = pokemonDao
        )
    }

    @Provides
    @Singleton
    fun provideGetPokemonDetailSource(
        apiService: PokemonApiService,
        @Named("PokemonRoomDBDaoImpl")
        pokemonDao: PokemonDao,
        @Named("PokemonAbilityRoomDBDaoImpl")
        pokemonAbilityDao: PokemonAbilityDao,
        @Named("PokemonMoveRoomDBDaoImpl")
        pokemonMoveDao: PokemonMoveDao,
        @Named("PokemonStatRoomDBDaoImpl")
        pokemonStatDao: PokemonStatDao,
        @Named("PokemonTypeRoomDBDaoImpl")
        pokemonTypeDao: PokemonTypeDao,
    ): GetPokemonDetailSource {
        return GetPokemonDetailSource(
            apiService = apiService,
            pokemonDao = pokemonDao,
            pokemonAbilityDao = pokemonAbilityDao,
            pokemonMoveDao = pokemonMoveDao,
            pokemonStatDao = pokemonStatDao,
            pokemonTypeDao = pokemonTypeDao,
        )
    }

    @Provides
    @Singleton
    fun provideRequestCatchingPokemonSource(
        apiService: PokemonPersonalApiService
    ): RequestCatchingPokemonSource {
        return RequestCatchingPokemonSource(
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun provideCatchPokemonSource(
        @Named("PokemonRoomDBDaoImpl")
        pokemonDao: PokemonDao,
    ): CatchPokemonSource {
        return CatchPokemonSource(
            pokemonDao = pokemonDao
        )
    }

    @Provides
    @Singleton
    fun provideReleasePokemonSource(
        apiService: PokemonPersonalApiService,
        @Named("PokemonRoomDBDaoImpl")
        pokemonDao: PokemonDao,
    ): ReleasePokemonSource {
        return ReleasePokemonSource(
            apiService = apiService,
            pokemonDao = pokemonDao
        )
    }

    @Provides
    @Singleton
    fun provideRenamePokemonSource(
        apiService: PokemonPersonalApiService,
        @Named("PokemonRoomDBDaoImpl")
        pokemonDao: PokemonDao,
    ): RenamePokemonSource {
        return RenamePokemonSource(
            apiService = apiService,
            pokemonDao = pokemonDao
        )
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        getPokemonListSource: GetPokemonListSource,
        getPokemonDetailSource: GetPokemonDetailSource,
        requestCatchingPokemonSource: RequestCatchingPokemonSource,
        catchPokemonSource: CatchPokemonSource,
        releasePokemonSource: ReleasePokemonSource,
        renamePokemonSource: RenamePokemonSource,
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            getPokemonListSource = getPokemonListSource,
            getPokemonDetailSource = getPokemonDetailSource,
            requestCatchingPokemonSource = requestCatchingPokemonSource,
            catchPokemonSource = catchPokemonSource,
            releasePokemonSource = releasePokemonSource,
            renamePokemonSource = renamePokemonSource
        )
    }
}