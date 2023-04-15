package co.harismiftahulhudha.phinconchallenge.core.database.di

import android.content.Context
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.ability.PokemonAbilityDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.move.PokemonMoveDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.stat.PokemonStatDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.type.PokemonTypeDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.AppRoomDB
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonRoomDBDaoImpl
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityRoomDBDaoImpl
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveRoomDBDaoImpl
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatRoomDBDaoImpl
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeRoomDBDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppRoomDB(@ApplicationContext context: Context) =
        AppRoomDB.getInstance(context = context)

    @Provides
    @Singleton
    fun providePokemonRoomDBDao(db: AppRoomDB) = db.pokemonRoomDBDao()

    @Provides
    @Singleton
    @Named("PokemonRoomDBDaoImpl")
    fun providePokemonDao(dao: PokemonRoomDBDao): PokemonDao = PokemonRoomDBDaoImpl(dao = dao)

    @Provides
    @Singleton
    fun providePokemonAbilityRoomDBDao(db: AppRoomDB) = db.pokemonAbilityRoomDBDao()

    @Provides
    @Singleton
    @Named("PokemonAbilityRoomDBDaoImpl")
    fun providePokemonAbilityDao(dao: PokemonAbilityRoomDBDao): PokemonAbilityDao =
        PokemonAbilityRoomDBDaoImpl(dao = dao)

    @Provides
    @Singleton
    fun providePokemonMoveRoomDBDao(db: AppRoomDB) = db.pokemonMoveRoomDBDao()

    @Provides
    @Singleton
    @Named("PokemonMoveRoomDBDaoImpl")
    fun providePokemonMoveDao(dao: PokemonMoveRoomDBDao): PokemonMoveDao =
        PokemonMoveRoomDBDaoImpl(dao = dao)

    @Provides
    @Singleton
    fun providePokemonStatRoomDBDao(db: AppRoomDB) = db.pokemonStatRoomDBDao()

    @Provides
    @Singleton
    @Named("PokemonStatRoomDBDaoImpl")
    fun providePokemonStatDao(dao: PokemonStatRoomDBDao): PokemonStatDao =
        PokemonStatRoomDBDaoImpl(dao = dao)

    @Provides
    @Singleton
    fun providePokemonTypeRoomDBDao(db: AppRoomDB) = db.pokemonTypeRoomDBDao()

    @Provides
    @Singleton
    @Named("PokemonTypeRoomDBDaoImpl")
    fun providePokemonTypeDao(dao: PokemonTypeRoomDBDao): PokemonTypeDao =
        PokemonTypeRoomDBDaoImpl(dao = dao)
}