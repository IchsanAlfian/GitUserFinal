package com.ichsanalfian.mygithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ichsanalfian.mygithubuser.api.ApiConfig
import com.ichsanalfian.mygithubuser.model.DetailUserResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailuserViewModel:ViewModel() {
    private val _Detailuser = MutableLiveData<DetailUserResponse>()
    val Detailuser: LiveData<DetailUserResponse> = _Detailuser

    fun getDetailUser(username: String) {
       ApiConfig.getApiService().getDetailUser(username)
           .enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful){
                    _Detailuser.postValue(response.body())
                }else{
                    Log.d("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("detailViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}