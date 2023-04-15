package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon

import androidx.room.Embedded
import androidx.room.Relation
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeEntity

data class PokemonDetailEntity(
    @Embedded
    val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonAbilityEntity::class
    )
    val abilities: List<PokemonAbilityEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonMoveEntity::class
    )
    val moves: List<PokemonMoveEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonStatEntity::class
    )
    val stats: List<PokemonStatEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonTypeEntity::class
    )
    val types: List<PokemonTypeEntity>,
)