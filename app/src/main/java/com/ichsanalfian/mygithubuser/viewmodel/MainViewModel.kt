package com.ichsanalfian.mygithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mygithubuser.data.FavUserRepository
import com.ichsanalfian.mygithubuser.model.ItemsItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: FavUserRepository) : ViewModel() {
    val listUser: LiveData<List<ItemsItem>> = repository.user
    val isLoading: LiveData<Boolean> = repository.isLoading
    fun getUser(query: String) {
        viewModelScope.launch {
            repository.getUser(query)
        }
    }

}