package com.ichsanalfian.mygithubuser.api


import com.ichsanalfian.mygithubuser.BuildConfig
import com.ichsanalfian.mygithubuser.model.DetailUserResponse
import com.ichsanalfian.mygithubuser.model.GithubResponse
import com.ichsanalfian.mygithubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("search/users")
    fun getUser(@Query("q") query: String?): Call<GithubResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}