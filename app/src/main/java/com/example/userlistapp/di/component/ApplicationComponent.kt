package com.example.userlistapp.di.component

import android.content.Context
import com.example.userlistapp.UserListApplication
import com.example.userlistapp.data.api.UserService
import com.example.userlistapp.data.repository.UserRepository
import com.example.userlistapp.di.ApplicationContext
import com.example.userlistapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: UserListApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): UserService

    fun getUserRepository(): UserRepository

}