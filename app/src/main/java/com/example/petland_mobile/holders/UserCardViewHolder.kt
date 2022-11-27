package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.PetClickListener
import com.example.petland_mobile.Interface.UserClickListener
import com.example.petland_mobile.databinding.UserCardCellBinding
import com.example.petland_mobile.models.Social
import com.example.petland_mobile.models.User

class UserCardViewHolder (private val cardCellBinding: UserCardCellBinding,
                          private val clickListener: UserClickListener)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindUser(username : String, avatarurl : String, userId : String) {
        cardCellBinding.friendlistUserName.text = username
        cardCellBinding.friendlistUserPhoto.setImageURI(avatarurl)

        cardCellBinding.userCardContainer.setOnClickListener {
            clickListener.onClick(userId)
        }
    }

}