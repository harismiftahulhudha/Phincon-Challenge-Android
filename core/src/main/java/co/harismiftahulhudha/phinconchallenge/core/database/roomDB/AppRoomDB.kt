package co.harismiftahulhudha.phinconchallenge.core.database.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.harismiftahulhudha.phinconchallenge.core.BuildConfig
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatRoomDBDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeRoomDBDao

@Database(
    entities = [
        PokemonEntity::class,
        PokemonAbilityEntity::class,
        PokemonMoveEntity::class,
        PokemonStatEntity::class,
        PokemonTypeEntity::class,
    ],
    version = 1
)
abstract class AppRoomDB : RoomDatabase() {
    companion object Factory {
        fun getInstance(context: Context): AppRoomDB {
            return if (BuildConfig.DEBUG) {
                Room.databaseBuilder(context, AppRoomDB::class.java, "phincon_challenge.db")
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build()
            } else {
                Room.databaseBuilder(context, AppRoomDB::class.java, "phincon_challenge.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    abstract fun pokemonRoomDBDao(): PokemonRoomDBDao
    abstract fun pokemonAbilityRoomDBDao(): PokemonAbilityRoomDBDao
    abstract fun pokemonMoveRoomDBDao(): PokemonMoveRoomDBDao
    abstract fun pokemonStatRoomDBDao(): PokemonStatRoomDBDao
    abstract fun pokemonTypeRoomDBDao(): PokemonTypeRoomDBDao
}