package com.example.userlistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.userlistapp.data.repository.UserRepository
import com.example.userlistapp.data.model.User
import com.example.userlistapp.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userDetail = MutableStateFlow<Resource<User>>(Resource.loading())
    val userDetail: StateFlow<Resource<User>> = _userDetail

    fun getUsersList(): Flow<PagingData<User>> {
        return userRepository.getUsers()
            .map { pagingData ->
                pagingData.map {
                    it
                }
            }
            .cachedIn(viewModelScope)
    }

    fun fetchUserDetails(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserDetails(userId)
                .catch { e ->
                    _userDetail.value = Resource.error(e.toString())
                }
                .collect {
                    _userDetail.value = Resource.success(it)
                }
        }
    }
}
