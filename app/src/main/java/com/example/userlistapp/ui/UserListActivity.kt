package com.example.userlistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlistapp.R
import com.example.userlistapp.databinding.ActivityUserListBinding
import com.example.userlistapp.ui.adapter.UserListAdapter
import com.example.userlistapp.viewmodel.UserListViewModel

class UserListActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserListViewModel>()
    private lateinit var adapter: UserListAdapter
    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        binding.viewModel = viewModel

        adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.userList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.getUsers()
    }
}
