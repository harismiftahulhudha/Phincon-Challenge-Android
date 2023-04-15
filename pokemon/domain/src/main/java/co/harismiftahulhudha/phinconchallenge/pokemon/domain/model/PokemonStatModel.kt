package co.harismiftahulhudha.phinconchallenge.pokemon.domain.model

data class PokemonStatModel(
    val id: Long,
    val pokemonId: Long,
    val name: String,
    val baseState: Int,
    val effort: Int,
)