package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val image: String,
    val isCaught: Boolean,
    val nickName: String,
    val indexNickName: Int
)
