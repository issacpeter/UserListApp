package com.example.userlistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
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
import com.example.userlistapp.ui.adapter.UserListAdapter
import com.example.userlistapp.utils.Status
import com.example.userlistapp.viewmodel.UserListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UserListViewModel
    @Inject
    lateinit var adapter: UserListAdapter
    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        binding.viewModel = viewModel

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
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

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { userList -> renderList(userList) }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@UserListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(userList: List<User>) {
        adapter.addData(userList)
        adapter.notifyDataSetChanged()
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
