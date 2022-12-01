package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardChatListCellBinding
import com.example.petland_mobile.holders.ChatListViewHolder
import com.example.petland_mobile.models.User

class ChatListAdapter (private val chatlist : List<User>)
    : RecyclerView.Adapter<ChatListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardChatListCellBinding.inflate(from, parent, false)
        return ChatListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bindUser(chatlist[position])
    }

    override fun getItemCount(): Int = chatlist.size

}