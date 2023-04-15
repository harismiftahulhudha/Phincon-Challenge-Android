package co.harismiftahulhudha.phinconchallenge.pokemon.domain.model

data class PokemonModel(
    val id: Long,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val image: String,
    val isCaught: Boolean,
    var nickName: String,
    val indexNickName: Int
)