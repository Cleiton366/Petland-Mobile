package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.UserCardCellBinding
import com.example.petland_mobile.holders.UserCardViewHolder
import com.example.petland_mobile.models.User

class UserCardAdapter (private val friendlist : List<User>)
    : RecyclerView.Adapter<UserCardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = UserCardCellBinding.inflate(from, parent, false)
        return UserCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        holder.bindUser(friendlist[position])
    }

    override fun getItemCount(): Int = friendlist.size
}