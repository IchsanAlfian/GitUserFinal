package com.ichsanalfian.mygithubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.api.ApiService
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import com.ichsanalfian.mygithubuser.local.room.FavUserDao
import com.ichsanalfian.mygithubuser.model.GithubResponse
import com.ichsanalfian.mygithubuser.model.ItemsItem
import com.ichsanalfian.mygithubuser.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavUserRepository private constructor(
    private val apiService: ApiService,
    private val favUserDao: FavUserDao,
    private val appExecutors: AppExecutors
){
    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }

        })
    }
    fun getFavoriteUser(): LiveData<List<FavUserEntity>>{
       return favUserDao.getFavoritedUser().asLiveData()
    }
    suspend fun setFavoritedUser(fav: FavUserEntity, favState: Boolean) {
        fav.isFavorited = favState
        favUserDao.insert(fav)
    }
    suspend fun deleteFavoriteUser(fav: FavUserEntity, favState: Boolean){
        fav.isFavorited = favState
        favUserDao.delete(fav)
    }
    companion object {
        @Volatile
        private var instance: FavUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavUserDao,
            appExecutors: AppExecutors
        ): FavUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavUserRepository(apiService, favUserDao, appExecutors)
            }.also { instance = it }
    }
}