package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.UserClickListener
import com.example.petland_mobile.databinding.UserCardCellBinding
import com.example.petland_mobile.holders.UserCardViewHolder
import com.example.petland_mobile.models.Social

class UserCardAdapter (private val friendlist : MutableList<Social>, private val viewType : String,
                       private val clickListener: UserClickListener)
    : RecyclerView.Adapter<UserCardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = UserCardCellBinding.inflate(from, parent, false)
        return UserCardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        if(viewType == "followers") {
            holder.bindUser(friendlist[position].follower_username,
                friendlist[position].follower_user_avatarurl,
                        friendlist[position].follower_user_id)
        } else holder.bindUser(friendlist[position].following_username,
            friendlist[position].following_user_avatarurl,
            friendlist[position].following_user_id)
    }

    override fun getItemCount(): Int = friendlist.size
}