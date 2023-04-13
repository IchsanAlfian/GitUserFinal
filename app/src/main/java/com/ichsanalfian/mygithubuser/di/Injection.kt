package com.ichsanalfian.mygithubuser.di

import android.content.Context
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.data.FavUserRepository
import com.ichsanalfian.mygithubuser.local.room.FavUserDatabase

object Injection {
    fun provideRepository(context: Context): FavUserRepository {
        ApiConfig.getApiService()
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()
        return FavUserRepository.getInstance(dao)
    }
}