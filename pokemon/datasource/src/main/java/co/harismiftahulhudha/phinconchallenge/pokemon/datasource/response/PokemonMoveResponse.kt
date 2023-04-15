package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response

import com.google.gson.annotations.SerializedName

data class PokemonMoveResponse(
    @SerializedName("move")
    val move: Move?
) {
    data class Move(
        @SerializedName("name")
        val name: String?,
    )
}
