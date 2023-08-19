package com.example.usergithub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usergithub.databinding.ItemListGithubuserBinding

class UsersAdapter(private val context: Context): ListAdapter<UserResponse, UsersAdapter.UserViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class UserViewHolder(val binding: ItemListGithubuserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.tvUsername.text = user.name ?: "Name not available"
            binding.tvLocation.text = user.location ?: "Location not available"
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivProfpic)

            binding.cvUserList.setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("username", user.login)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListGithubuserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}