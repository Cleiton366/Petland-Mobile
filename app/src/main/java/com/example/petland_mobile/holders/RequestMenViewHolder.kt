package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardRequestCellBinding
import com.example.petland_mobile.databinding.UserCardCellBinding
import com.example.petland_mobile.models.User

class RequestMenViewHolder(private val cardRequestCellBinding: CardRequestCellBinding)
    : RecyclerView.ViewHolder(cardRequestCellBinding.root) {
    fun bindUser(user : User) {
        cardRequestCellBinding.requestListUserName.text = user.username
        cardRequestCellBinding.requestListUserPhoto.setImageURI(user.avatarurl)
    }
}