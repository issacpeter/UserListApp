package com.example.userlistapp.data

import androidx.lifecycle.LiveData
import com.example.userlistapp.data.api.UserService

class UserRepository(private val userService: UserService) {
//    suspend fun getUsers(): List<User> = userService.getUsers()
    suspend fun getUsers(): List<User> = getDummyUsers()

    private fun getDummyUsers(): List<User> {
        var usersList: ArrayList<User> = arrayListOf()
        usersList.add(User(1, "Ram", "ram@dummy.com", "", "I am good"))
        usersList.add(User(2, "Laxman", "ram@dummy.com", "", "I am good"))
        usersList.add(User(3, "Bharat", "ram@dummy.com", "", "I am good"))
        usersList.add(User(4, "Shatru", "ram@dummy.com", "", "I am good"))
        usersList.add(User(5, "Hanuman", "ram@dummy.com", "", "I am good"))
        usersList.add(User(6, "Ravan", "ram@dummy.com", "", "I am good"))
        usersList.add(User(7, "Seetha", "ram@dummy.com", "", "I am good"))
        usersList.add(User(8, "Geetha", "ram@dummy.com", "", "I am good"))
        return usersList
    }

    suspend fun getUserDetails(userId: Int): User = userService.getUserDetails(userId)
}