package com.ichsanalfian.mygithubuser.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity

@Database(entities = [FavUserEntity::class], version = 1, exportSchema = false)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var instance: FavUserDatabase? = null
        fun getInstance(context: Context): FavUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDatabase::class.java, "User.db"
                ).build()
            }
    }
}