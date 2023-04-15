package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.BuildConfig
import co.harismiftahulhudha.phinconchallenge.core.database.dao.pokemon.PokemonDao
import co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon.PokemonEntity
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.SourceResult
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.toModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel

class GetPokemonListSource(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) {
    suspend operator fun invoke(isCaught: Boolean, page: Int): SourceResult<List<PokemonModel>> {
        val limit = BuildConfig.LIMIT.toInt()
        val offset = (page - 1) * limit
        return try {
            val response = if (isCaught) {
                apiService.getMyPokemon(offset = offset)
            } else {
                apiService.getList(offset = offset)
            }
            if (response.isSuccessful) {
                response.body()?.result?.let { list ->
                    list.forEach { pokemon ->
                        pokemon?.let { pokemonResponse ->
                            var url = pokemonResponse.url.orBlank()
                            val id = if (url.isNotBlank() && url.length > 2) {
                                url = url.substring(0, url.length - 1)
                                val splitSlash = url.split("/")
                                splitSlash[splitSlash.size - 1].toLong()
                            } else {
                                1L
                            }
                            val pokemonEntity = pokemonDao.getPokemon(id)
                            //TODO Perlu di revisi setelah ada API my pokemon
                            if (pokemonEntity == null) {
                                pokemonDao.insert(PokemonEntity(
                                    id = id,
                                    name = pokemonResponse.name.orBlank(),
                                    baseExperience = 0,
                                    height = 0,
                                    weight = 0,
                                    image = "${BuildConfig.BASE_POKEMON_IMAGE_URL}$id.png",
                                    isCaught = false,
                                    nickName = "",
                                    indexNickName = -1
                                ))
                            }
                        }
                    }
                }
                SourceResult.Success(
                    data = if (isCaught) pokemonDao.getMyPokemon(offset).map { it.toModel() } else pokemonDao.getAll(offset).map { it.toModel() }
                )
            } else {
                SourceResult.Failure(
                    error = General(response.message()),
                    data = if (isCaught) pokemonDao.getMyPokemon(offset).map { it.toModel() } else pokemonDao.getAll(offset).map { it.toModel() }
                )
            }
        } catch (e: Exception) {
            SourceResult.Failure(
                error = General(e.message.orBlank()),
                data = if (isCaught) pokemonDao.getMyPokemon(offset).map { it.toModel() } else pokemonDao.getAll(offset).map { it.toModel() }
            )
        }
    }
}