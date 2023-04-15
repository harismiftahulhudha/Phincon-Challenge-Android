package co.harismiftahulhudha.phinconchallenge.pokemon.datasource.source

import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseApiResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PokemonPersonalApiService {
    @GET("catch")
    suspend fun catch(): Response<BaseApiResponse<String>>

    @GET("release")
    suspend fun release(): Response<BaseApiResponse<Int>>

    @FormUrlEncoded
    @POST("rename")
    suspend fun rename(
        @Field("index") index: Int,
        @Field("nickname") nickname: String
    ): Response<BaseApiResponse<String>>
}