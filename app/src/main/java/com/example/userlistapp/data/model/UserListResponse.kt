package com.example.userlistapp.data.model

data class UserListResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)