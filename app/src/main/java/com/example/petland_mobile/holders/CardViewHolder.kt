package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardCellBinding
import com.example.petland_mobile.models.Pet

class CardViewHolder (private val cardCellBinding: CardCellBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindPet(petForm : Pet) {
        cardCellBinding.petName.text = "Name: ${petForm.petname}"
        cardCellBinding.petAge.text = "Age: ${petForm.age}"
        cardCellBinding.petPhoto.setImageURI(petForm.petphoto)
    }
}