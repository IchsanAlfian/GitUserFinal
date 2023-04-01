package com.ichsanalfian.mygithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.data.FavUserRepository
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import com.ichsanalfian.mygithubuser.model.GithubResponse
import com.ichsanalfian.mygithubuser.model.ItemsItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: FavUserRepository) : ViewModel() {
    val listUser : LiveData<List<ItemsItem>> = repository.user
    val isLoading: LiveData<Boolean> = repository.isLoading
    fun getUser(query: String) {
        viewModelScope.launch {
            repository.getUser(query)
        }
    }
//    fun getFavoritedUser() = repository.getFavoriteUser()
//    fun saveFavUser(fav: FavUserEntity) {
//        repository.setFavoritedUser(fav, true)
//    }
//    fun deleteFavUser(fav: FavUserEntity) {
//        repository.setFavoritedUser(fav, false)
//    }


}