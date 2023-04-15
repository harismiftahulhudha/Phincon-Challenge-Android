package co.harismiftahulhudha.phinconchallenge.pokemon.domain.model

data class PokemonDetailModel(
    val pokemon: PokemonModel,
    val abilities: List<PokemonAbilityModel>,
    val moves: List<PokemonMoveModel>,
    val stats: List<PokemonStatModel>,
    val types: List<PokemonTypeModel>,
)