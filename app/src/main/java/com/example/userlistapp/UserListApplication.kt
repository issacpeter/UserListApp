package com.example.userlistapp

import android.app.Application
import com.example.userlistapp.di.component.ApplicationComponent
import com.example.userlistapp.di.component.DaggerApplicationComponent
import com.example.userlistapp.di.module.ApplicationModule

class UserListApplication  : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}