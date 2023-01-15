package com.example.userlistapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.userlistapp.data.repository.UserRepository
import com.example.userlistapp.di.ActivityContext
import com.example.userlistapp.ui.adapter.UserListAdapter
import com.example.userlistapp.ui.base.ViewModelProviderFactory
import com.example.userlistapp.viewmodel.UserListViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideUserListViewModel(userRepository: UserRepository): UserListViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(UserListViewModel::class) {
                UserListViewModel(userRepository)
            })[UserListViewModel::class.java]
    }

    @Provides
    fun provideUserListAdapter() = UserListAdapter(ArrayList())

}