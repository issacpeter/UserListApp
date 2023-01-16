package com.example.userlistapp.data.api

import com.example.userlistapp.data.model.User
import com.example.userlistapp.data.model.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface UserService {

    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserListResponse

    @GET("users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: Int): User
}