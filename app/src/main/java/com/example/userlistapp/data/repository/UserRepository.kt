package com.example.userlistapp.data.repository

import com.example.userlistapp.data.model.User
import com.example.userlistapp.data.api.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userService: UserService) {

    fun getUsers(): Flow<List<User>> {
        return flow {
            emit(userService.getUsers())
        }.map {
            it.users
        }
    }

    fun getUserDetails(userId: Int): Flow<User> {
        return flow {
            emit(userService.getUserDetails(userId))
        }.map {
            it
        }
    }
//    suspend fun getUsers(): List<User> = getDummyUsers()

//    suspend fun getUserDetails(userId: Int): User = userService.getUserDetails(userId)
}