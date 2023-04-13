package com.ichsanalfian.mygithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.model.DetailUserResponse
import com.ichsanalfian.mygithubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailuserViewModel : ViewModel() {
    private val _detailuser = MutableLiveData<DetailUserResponse>()
    val detailuser: LiveData<DetailUserResponse> = _detailuser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val follower: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following
    fun getDetailUser(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailuser.postValue(response.body())
                    } else {
                        Log.d("MainViewModel", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("DetailUserViewModel", "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.postValue(response.body())
                } else {
                    Log.e("DetailUserViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailUserViewModel", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.postValue(response.body())
                } else {
                    Log.e("DetailUserViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailUserViewModel", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }
}