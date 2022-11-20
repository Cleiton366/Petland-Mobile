package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardChatListCellBinding
import com.example.petland_mobile.models.User


class ChatListViewHolder (private val cardChatListCellBinding: CardChatListCellBinding)
    : RecyclerView.ViewHolder(cardChatListCellBinding.root)  {
    fun bindUser(user : User) {
        cardChatListCellBinding.chatListUserName.text = user.username
        cardChatListCellBinding.chatListUserPhoto.setImageURI(user.avatarurl)
    }
}