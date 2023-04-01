package com.ichsanalfian.mygithubuser.di

import android.content.Context
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.data.FavUserRepository
import com.ichsanalfian.mygithubuser.local.room.FavUserDatabase
import com.ichsanalfian.mygithubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()
        val appExecutors = AppExecutors()
        return FavUserRepository.getInstance(apiService, dao, appExecutors)
    }
}