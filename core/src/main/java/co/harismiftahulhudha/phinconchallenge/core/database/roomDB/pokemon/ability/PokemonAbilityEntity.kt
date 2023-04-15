package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_ability")
data class PokemonAbilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pokemonId: Long,
    val name: String,
)