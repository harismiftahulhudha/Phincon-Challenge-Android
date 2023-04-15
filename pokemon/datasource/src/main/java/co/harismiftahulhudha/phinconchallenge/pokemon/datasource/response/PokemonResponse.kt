package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response

import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonDetailEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonEntity
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orFalse
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orMinusOne
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orZero
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.*
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityResponse?>?,
    @SerializedName("moves")
    val moves: List<PokemonMoveResponse?>?,
    @SerializedName("stats")
    val stats: List<PokemonStatResponse?>?,
    @SerializedName("types")
    val types: List<PokemonTypeResponse?>?,
)

fun PokemonEntity?.toModel(): PokemonModel {
    return PokemonModel(
        id = this?.id.orZero(), name = this?.name.orBlank(),
        baseExperience = this?.baseExperience.orZero(), height = this?.height.orZero(),
        weight = this?.weight.orZero(), image = this?.image.orBlank(),
        isCaught = this?.isCaught.orFalse(), nickName = this?.nickName.orBlank(),
        indexNickName = this?.indexNickName.orMinusOne()
    )
}

fun PokemonDetailEntity.toModel(): PokemonDetailModel {
    return PokemonDetailModel(
        pokemon = PokemonModel(
            id = this.pokemon.id,
            name = this.pokemon.name,
            baseExperience = this.pokemon.baseExperience,
            height = this.pokemon.height,
            weight = this.pokemon.weight,
            image = this.pokemon.image,
            isCaught = this.pokemon.isCaught,
            nickName = this.pokemon.nickName,
            indexNickName = this.pokemon.indexNickName,
        ),
        abilities = abilities.map { PokemonAbilityModel(id = it.id, pokemonId = it.pokemonId, name = it.name) },
        moves = moves.map { PokemonMoveModel(id = it.id, pokemonId = it.pokemonId, name = it.name) },
        stats = stats.map { PokemonStatModel(id = it.id, pokemonId = it.pokemonId, name = it.name, baseState = it.baseState, effort = it.effort) },
        types = types.map { PokemonTypeModel(id = it.id, pokemonId = it.pokemonId, name = it.name) }
    )
}
