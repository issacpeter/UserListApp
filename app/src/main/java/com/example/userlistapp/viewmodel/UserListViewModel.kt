package com.example.userlistapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlistapp.data.User
import com.example.userlistapp.data.UserRepository
import com.example.userlistapp.data.api.RetrofitClient
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val userService = RetrofitClient().createUserService()
    private val userRepository = UserRepository(userService)
    var userList = MutableLiveData<List<User>>()
    var userDetails = MutableLiveData<User>()

    fun getUsers() {
        viewModelScope.launch {
            userList.value = userRepository.getUsers()
        }
    }

    fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            userDetails.value = userRepository.getUserDetails(userId)
        }
    }
}
