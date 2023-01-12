package com.example.userlistapp.data.api

import com.example.userlistapp.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: Int): User
}