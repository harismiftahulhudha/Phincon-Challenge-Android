package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response

import com.google.gson.annotations.SerializedName

data class PokemonStatResponse(
    @SerializedName("base_stat")
    val baseStat: Int?,
    @SerializedName("effort")
    val effort: Int?,
    @SerializedName("stat")
    val stat: Stat?
) {
    data class Stat(
        @SerializedName("name")
        val name: String?,
    )
}
