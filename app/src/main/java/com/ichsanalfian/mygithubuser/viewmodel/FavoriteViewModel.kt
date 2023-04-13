package com.ichsanalfian.mygithubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mygithubuser.data.FavUserRepository
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavUserRepository) : ViewModel() {
    fun getUserFavorited() = repository.getFavoriteUser()
    fun saveOrDeleteUser(fav: FavUserEntity, isFavorited: Boolean) {
        viewModelScope.launch {
            if (isFavorited) {
                repository.deleteFavoritedUser(fav, false)
            } else {
                repository.setFavoritedUser(fav, true)
            }
        }
    }
}