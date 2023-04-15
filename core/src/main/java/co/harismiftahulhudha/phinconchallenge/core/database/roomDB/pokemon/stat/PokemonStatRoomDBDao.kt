package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonStatRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PokemonStatEntity): Long

    @Query("SELECT * FROM pokemon_stat WHERE pokemonId = :pokemonId AND name = :name")
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonStatEntity?
}