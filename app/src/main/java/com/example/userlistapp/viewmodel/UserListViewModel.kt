package com.example.userlistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlistapp.data.repository.UserRepository
import com.example.userlistapp.data.model.User
import com.example.userlistapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserListViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userList = MutableStateFlow<Resource<List<User>>>(Resource.loading())
    val userList: StateFlow<Resource<List<User>>> = _userList
    private val _userDetail = MutableStateFlow<Resource<User>>(Resource.loading())
    val userDetail: StateFlow<Resource<User>> = _userDetail

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            userRepository.getUsers()
                .catch { e ->
                    _userList.value = Resource.error(e.toString())
                }
                .collect {
                    _userList.value = Resource.success(it)
                }
        }
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
