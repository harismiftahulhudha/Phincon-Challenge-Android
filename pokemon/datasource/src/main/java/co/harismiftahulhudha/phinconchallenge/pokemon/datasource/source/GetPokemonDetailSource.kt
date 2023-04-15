package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.ability.PokemonAbilityDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.move.PokemonMoveDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.stat.PokemonStatDao
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.type.PokemonTypeDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.ability.PokemonAbilityEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.move.PokemonMoveEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.stat.PokemonStatEntity
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.type.PokemonTypeEntity
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orZero
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.toModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonDetailSource(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao,
    private val pokemonAbilityDao: PokemonAbilityDao,
    private val pokemonMoveDao: PokemonMoveDao,
    private val pokemonStatDao: PokemonStatDao,
    private val pokemonTypeDao: PokemonTypeDao,
) {
    suspend operator fun invoke(id: Long): Flow<SourceResult<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>>> = flow {
        try {
            emit(SourceResult.Loading(getPokemonDetail(id)))
            val response = apiService.getDetail(id)
            if (response.isSuccessful) {
                response.body()?.let { pokemonResponse ->
                    pokemonDao.updateDetail(
                        baseExperience = pokemonResponse.baseExperience.orZero(),
                        height = pokemonResponse.height.orZero(),
                        weight = pokemonResponse.weight.orZero(),
                        id = id
                    )
                    pokemonResponse.abilities?.let { abilities ->
                        abilities.forEach {
                            it?.let { ability ->
                                val pokemonAbility = pokemonAbilityDao.getByPokemonIdAndName(
                                    pokemonId = id,
                                    name = ability.ability?.name.orBlank()
                                )
                                if (pokemonAbility == null) {
                                    pokemonAbilityDao.insert(
                                        data = PokemonAbilityEntity(
                                            id = 0,
                                            pokemonId = id,
                                            name = ability.ability?.name.orBlank()
                                        )
                                    )
                                }
                            }
                        }
                    }
                    pokemonResponse.moves?.let { moves ->
                        moves.forEach {
                            it?.let { move ->
                                val pokemonAbility = pokemonMoveDao.getByPokemonIdAndName(
                                    pokemonId = id,
                                    name = move.move?.name.orBlank()
                                )
                                if (pokemonAbility == null) {
                                    pokemonMoveDao.insert(
                                        data = PokemonMoveEntity(
                                            id = 0, pokemonId = id, name = move.move?.name.orBlank()
                                        )
                                    )
                                }
                            }
                        }
                    }
                    pokemonResponse.stats?.let { stats ->
                        stats.forEach {
                            it?.let { stat ->
                                val pokemonAbility = pokemonStatDao.getByPokemonIdAndName(
                                    pokemonId = id,
                                    name = stat.stat?.name.orBlank()
                                )
                                if (pokemonAbility == null) {
                                    pokemonStatDao.insert(
                                        data = PokemonStatEntity(
                                            id = 0,
                                            pokemonId = id,
                                            name = stat.stat?.name.orBlank(),
                                            baseState = stat.baseStat.orZero(),
                                            effort = stat.effort.orZero()
                                        )
                                    )
                                }
                            }
                        }
                    }
                    pokemonResponse.types?.let { types ->
                        types.forEach {
                            it?.let { type ->
                                val pokemonAbility = pokemonTypeDao.getByPokemonIdAndName(
                                    pokemonId = id,
                                    name = type.type?.name.orBlank()
                                )
                                if (pokemonAbility == null) {
                                    pokemonTypeDao.insert(
                                        data = PokemonTypeEntity(
                                            id = 0, pokemonId = id, name = type.type?.name.orBlank()
                                        )
                                    )
                                }
                            }
                        }
                    }

                    emit(SourceResult.Success(getPokemonDetail(id)))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                SourceResult.Failure(
                    General(e.message.orBlank()),
                    getPokemonDetail(id)
                )
            )
        }
    }

    private suspend fun getPokemonDetail(id: Long): Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>> {
        val detail = pokemonDao.getDetail(id).toModel()

        // About
        val dataAbout: MutableList<DetailModel> = mutableListOf()
        val status = if (detail.pokemon.isCaught) {
            "Already caught"
        } else {
            "Haven't been caught yet"
        }
        dataAbout.add(DetailModel(
            label = "Status", value = status
        ))
        dataAbout.add(DetailModel(
            label = "Nickname", value = detail.pokemon.nickName.ifBlank { "-" }
        ))
        dataAbout.add(DetailModel(
            label = "Height", value = detail.pokemon.height.toString()
        ))
        dataAbout.add(DetailModel(
            label = "Weight", value = detail.pokemon.weight.toString()
        ))
        dataAbout.add(DetailModel(
            label = "Base Experience", value = detail.pokemon.baseExperience.toString()
        ))
        var types = ""
        detail.types.forEachIndexed { index, type ->
            types += type.name.replaceFirstChar { it.uppercase() }
            if (index < detail.types.size - 1) {
                types += ", "
            }
        }
        dataAbout.add(DetailModel(
            label = "Types", value = types
        ))
        var abilities = ""
        detail.abilities.forEachIndexed { index, ability ->
            abilities += ability.name.replaceFirstChar { it.uppercase() }
            if (index < detail.abilities.size - 1) {
                abilities += ", "
            }
        }
        dataAbout.add(DetailModel(
            label = "Abilities", value = abilities
        ))

        // Stat
        val dataStats: MutableList<DetailModel> = mutableListOf()
        detail.stats.forEach { stat ->
            dataStats.add(DetailModel(
                label = stat.name.replaceFirstChar { it.uppercase() }, value = stat.baseState.toString()
            ))
        }

        // Move
        val dataMoves: MutableList<DetailModel> = mutableListOf()
        var moves = ""
        detail.moves.forEachIndexed { index, move ->
            moves += move.name.replaceFirstChar { it.uppercase() }
            if (index < detail.moves.size - 1) {
                moves += ","
            }
        }
        dataMoves.add(DetailModel(
            label = "Moves", value = moves
        ))
        return Pair(detail, Triple(dataAbout, dataStats, dataMoves))
    }
}