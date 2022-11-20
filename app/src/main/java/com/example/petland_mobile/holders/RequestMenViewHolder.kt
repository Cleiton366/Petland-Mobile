package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.ChatClickListener
import com.example.petland_mobile.databinding.CardRequestCellBinding
import com.example.petland_mobile.models.User

class RequestMenViewHolder(private val cardRequestCellBinding: CardRequestCellBinding,
                           private var clickListener: ChatClickListener
)
    : RecyclerView.ViewHolder(cardRequestCellBinding.root) {
    fun bindUser(user : User) {
        cardRequestCellBinding.requestListUserName.text = user.username
        cardRequestCellBinding.requestListUserPhoto.setImageURI(user.avatarurl)

        cardRequestCellBinding.acceptButton.setOnClickListener{
            clickListener.chatListRequest(user)
        }
    }
}