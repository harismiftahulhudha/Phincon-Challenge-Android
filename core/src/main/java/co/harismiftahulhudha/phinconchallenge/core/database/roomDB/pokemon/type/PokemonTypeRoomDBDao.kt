package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonTypeRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PokemonTypeEntity): Long

    @Query("SELECT * FROM pokemon_type WHERE pokemonId = :pokemonId AND name = :name")
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonTypeEntity?
}