package com.ichsanalfian.mygithubuser.api

import com.ichsanalfian.mygithubuser.model.DetailUserResponse
import com.ichsanalfian.mygithubuser.model.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") query : String?
    ): Call<GithubResponse>
    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String)
    : Call<DetailUserResponse>
}