package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_stat")
data class PokemonStatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pokemonId: Long,
    val name: String,
    val baseState: Int,
    val effort: Int,
)