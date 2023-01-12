package com.example.userlistapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://example.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createUserService(): UserService = retrofit.create(UserService::class.java)
}