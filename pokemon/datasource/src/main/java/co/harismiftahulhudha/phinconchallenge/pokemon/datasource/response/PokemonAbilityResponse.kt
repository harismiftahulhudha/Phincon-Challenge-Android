package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response

import com.google.gson.annotations.SerializedName

data class PokemonAbilityResponse(
    @SerializedName("ability")
    val ability: Ability?
) {
    data class Ability(
        @SerializedName("name")
        val name: String?,
    )
}
