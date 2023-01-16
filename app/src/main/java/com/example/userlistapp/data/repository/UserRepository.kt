package com.example.userlistapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.userlistapp.data.model.User
import com.example.userlistapp.data.api.UserService
import com.example.userlistapp.data.model.UserListResponse
import com.example.userlistapp.data.paging.UserListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userService: UserService) {

    val NETWORK_PAGE_SIZE = 10

    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserListPagingSource(service = userService)
            }
        ).flow
    }

    fun getUserDetails(userId: Int): Flow<User> {
        return flow {
            emit(userService.getUserDetails(userId))
        }.map {
            it
        }
    }
}