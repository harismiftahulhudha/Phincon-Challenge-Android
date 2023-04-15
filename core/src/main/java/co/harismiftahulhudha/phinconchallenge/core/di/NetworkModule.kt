package co.harismiftahulhudha.phinconchallenge.core.di

import android.content.Context
import co.harismiftahulhudha.phinconchallenge.core.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    @Named("RetrofitBasePokemon")
    fun provideRetrofitBasePokemon(@ApplicationContext context: Context): Retrofit {
        val timeout = 2L
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MINUTES)
            .readTimeout(timeout, TimeUnit.MINUTES)
            .writeTimeout(timeout, TimeUnit.MINUTES)
        val chuckerInterceptorBuilder = ChuckerInterceptor.Builder(context)
        val httpCacheDirectory = File(context.cacheDir, "responses")
        try {
            val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            httpClient.cache(cache)
        } catch (e: IOException) {
            Timber.e(e)
        }

        httpClient.apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                addInterceptor(chuckerInterceptorBuilder.build())
            }
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_POKEMON_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("RetrofitBasePersonalPokemon")
    fun provideRetrofitPersonalPokemon(@ApplicationContext context: Context): Retrofit {
        val timeout = 2L
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MINUTES)
            .readTimeout(timeout, TimeUnit.MINUTES)
            .writeTimeout(timeout, TimeUnit.MINUTES)
        val chuckerInterceptorBuilder = ChuckerInterceptor.Builder(context)
        val httpCacheDirectory = File(context.cacheDir, "responses")
        try {
            val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            httpClient.cache(cache)
        } catch (e: IOException) {
            Timber.e(e)
        }

        httpClient.apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                addInterceptor(chuckerInterceptorBuilder.build())
            }
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_PERSONAL_POKEMON_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }
}