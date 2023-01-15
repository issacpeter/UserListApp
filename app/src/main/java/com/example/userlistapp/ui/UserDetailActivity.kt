package com.example.userlistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.userlistapp.R
import com.example.userlistapp.UserListApplication
import com.example.userlistapp.databinding.ActivityUserDetailBinding
import com.example.userlistapp.databinding.ActivityUserListBinding
import com.example.userlistapp.di.component.DaggerActivityComponent
import com.example.userlistapp.di.module.ActivityModule
import com.example.userlistapp.utils.Status
import com.example.userlistapp.viewmodel.UserListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UserListViewModel
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)

        val userId = intent.getIntExtra("id", 0)
        viewModel.fetchUserDetails(userId)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userDetail.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { user -> binding.user = user }
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@UserDetailActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as UserListApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

}