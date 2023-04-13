package com.ichsanalfian.mygithubuser.local.room

import androidx.room.*
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favUser: FavUserEntity)

    @Update
    fun update(favUser: FavUserEntity)

    @Delete
    suspend fun delete(favUser: FavUserEntity)

    @Query("SELECT * FROM FavUser where favorited = 1")
    fun getFavoritedUser(): Flow<List<FavUserEntity>>


}