package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response

import com.google.gson.annotations.SerializedName

data class PokemonTypeResponse(
    @SerializedName("type")
    val type: Type?
) {
    data class Type(
        @SerializedName("name")
        val name: String?,
    )
}
