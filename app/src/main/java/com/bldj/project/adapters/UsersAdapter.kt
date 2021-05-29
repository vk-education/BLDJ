package com.bldj.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.databinding.UserItemContainerBinding
import data.User

class UsersAdapter(usersList: MutableList<User>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    private var users: MutableList<User> = usersList

    inner class UsersViewHolder(userItemContainerBinding: UserItemContainerBinding) :
        RecyclerView.ViewHolder(userItemContainerBinding.root) {
        private var userItemBinding: UserItemContainerBinding = userItemContainerBinding

         fun bindUser(user: User) {
             if(user.name.length>18){
                 user.name = user.name.substring(0,18)+"..."
             }
            userItemBinding.user = user
            userItemBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        val userBinding: UserItemContainerBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.user_item_container, parent, false)
        return UsersViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindUser(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}