package com.example.userlistapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.userlistapp.data.model.User
import com.example.userlistapp.databinding.UserItemBinding
import com.example.userlistapp.ui.UserDetailActivity


class UserListAdapter(
    private val userList: ArrayList<User>
) : RecyclerView.Adapter<UserListAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
            itemView.setOnClickListener {
                var intent = Intent(binding.root!!.context, UserDetailActivity::class.java)
                intent.putExtra("id", user.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(userList[position])

    fun addData(list: List<User>) {
        userList.addAll(list)
    }

}
