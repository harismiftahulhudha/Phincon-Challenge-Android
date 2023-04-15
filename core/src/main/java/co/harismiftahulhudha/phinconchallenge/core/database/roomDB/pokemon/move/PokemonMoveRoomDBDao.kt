package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonMoveRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PokemonMoveEntity): Long

    @Query("SELECT * FROM pokemon_move WHERE pokemonId = :pokemonId AND name = :name")
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonMoveEntity?
}