package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_type")
data class PokemonTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pokemonId: Long,
    val name: String,
)