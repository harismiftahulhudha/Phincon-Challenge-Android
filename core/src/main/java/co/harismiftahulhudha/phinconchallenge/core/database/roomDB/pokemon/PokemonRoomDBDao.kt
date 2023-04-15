package co.harismiftahulhudha.phinconchallenge.core.database.roomDB.pokemon

import androidx.room.*
import co.harismiftahulhudha.phinconchallenge.core.BuildConfig

@Dao
interface PokemonRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PokemonEntity): Long

    @Query("SELECT * FROM pokemon ORDER BY id ASC LIMIT ${BuildConfig.LIMIT} OFFSET :offset")
    suspend fun getAll(offset: Int): List<PokemonEntity>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getDetail(id: Long): PokemonDetailEntity

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemon(id: Long): PokemonEntity?

    @Query("SELECT * FROM pokemon WHERE isCaught = 1 ORDER BY id ASC LIMIT ${BuildConfig.LIMIT} OFFSET :offset")
    suspend fun getMyPokemon(offset: Int): List<PokemonEntity>

    @Query("UPDATE pokemon SET baseExperience = :baseExperience, height = :height, weight = :weight WHERE id = :id")
    suspend fun updateDetail(baseExperience: Int, height: Int, weight: Int, id: Long)

    @Query("UPDATE pokemon SET isCaught = :isCaught, nickName = :nickName, indexNickName = :indexNickName WHERE id = :id")
    suspend fun updateCatching(isCaught: Boolean, nickName: String, indexNickName: Int, id: Long)
}