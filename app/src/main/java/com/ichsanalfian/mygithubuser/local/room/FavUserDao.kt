package com.ichsanalfian.mygithubuser.local.room

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favUser : FavUserEntity)

    @Update
    fun update(favUser : FavUserEntity)

    @Delete
    suspend fun delete(favUser : FavUserEntity)

    @Query("SELECT * FROM FavUser where favorited = 1")
    fun getFavoritedUser(): Flow<List<FavUserEntity>>
    @Query("SELECT EXISTS(SELECT * FROM FavUser WHERE login = :username AND favorited = 1)")
    suspend fun isUserFavorited(username: String): Boolean

}