package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.BuildConfig
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.PokemonListResponse
import co.harismiftahulhudha.phinconchallenge.pokemon.datasource.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("v2/pokemon")
    suspend fun getList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = BuildConfig.LIMIT.toInt(),
    ): Response<PokemonListResponse>
    @GET("v2/pokemon")
    suspend fun getMyPokemon(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = BuildConfig.LIMIT.toInt(),
    ): Response<PokemonListResponse>

    @GET("v2/pokemon/{id}")
    suspend fun getDetail(
        @Path("id") id: Long
    ): Response<PokemonResponse>
}