package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_move")
data class PokemonMoveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pokemonId: Long,
    val name: String,
)