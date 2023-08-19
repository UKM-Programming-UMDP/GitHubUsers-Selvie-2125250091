package com.example.usergithub

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getListUser(): Call<List<UserResponse>>

    @GET("users/{username}")
    fun getUserDetailsByUsername(@Path("username") username: String): Call<UserResponse>
}