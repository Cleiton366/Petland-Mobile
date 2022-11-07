package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.UserCardCellBinding
import com.example.petland_mobile.models.User

class UserCardViewHolder (private val cardCellBinding: UserCardCellBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindUser(user : User) {
        cardCellBinding.friendlistUserName.text = user.username
        cardCellBinding.friendlistUserPhoto.setImageURI(user.avatarurl)
    }
}