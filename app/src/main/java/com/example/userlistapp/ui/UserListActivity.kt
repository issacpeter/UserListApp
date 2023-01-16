package com.example.userlistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlistapp.R
import com.example.userlistapp.UserListApplication
import com.example.userlistapp.data.model.User
import com.example.userlistapp.databinding.ActivityUserListBinding
import com.example.userlistapp.di.component.DaggerActivityComponent
import com.example.userlistapp.di.module.ActivityModule
import com.example.userlistapp.ui.adapter.UsersAdapter
import com.example.userlistapp.utils.Status
import com.example.userlistapp.viewmodel.UserListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UserListViewModel
    @Inject
    lateinit var adapter: UsersAdapter
    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        binding.viewModel = viewModel

        setupUI()
        collectUiState()
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewModel.getUsersList().collect { users ->
                binding.progressBar.visibility = View.GONE
                Log.e("@@@TAG", "collectUiState: ")
                adapter.submitData(users)
            }
        }
    }

    private fun setupUI() {
        supportActionBar?.title = getString(R.string.home)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
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
