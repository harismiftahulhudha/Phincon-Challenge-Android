package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonAbilityRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PokemonAbilityEntity): Long

    @Query("SELECT * FROM pokemon_ability WHERE pokemonId = :pokemonId AND name = :name")
    suspend fun getByPokemonIdAndName(pokemonId: Long, name: String): PokemonAbilityEntity?
}