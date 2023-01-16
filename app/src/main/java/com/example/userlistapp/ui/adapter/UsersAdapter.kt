package com.example.userlistapp.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userlistapp.data.model.User
import com.example.userlistapp.databinding.UserItemBinding
import com.example.userlistapp.ui.UserDetailActivity


class UsersAdapter : PagingDataAdapter<User, UsersViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        Log.e("@@@TAG", "onBindViewHolder: "+ getItem(position).toString())
        getItem(position)?.let { holder.bind(it) }
    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class UsersViewHolder(
    val binding: UserItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.user = user
        val path = user.image
        path?.let {
            binding.ivUser
                Glide.with(binding.root.context)
                    .load(path)
                    .circleCrop()
                    .into(binding.ivUser);
            }
            itemView.setOnClickListener {
                var intent = Intent(binding.root!!.context, UserDetailActivity::class.java)
                intent.putExtra("id", user.id)
                binding.root.context.startActivity(intent)
            }
        }
    }