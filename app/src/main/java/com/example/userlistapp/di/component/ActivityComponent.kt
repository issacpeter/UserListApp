package com.example.userlistapp.di.component

import com.example.userlistapp.di.ActivityScope
import com.example.userlistapp.di.module.ActivityModule
import com.example.userlistapp.ui.UserDetailActivity
import com.example.userlistapp.ui.UserListActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: UserListActivity)

    fun inject(activity: UserDetailActivity)

}