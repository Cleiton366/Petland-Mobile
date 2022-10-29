package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardCellBinding
import com.example.petland_mobile.models.Pet

class CardViewHolder (private val cardCellBinding: CardCellBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindPet(petForm : Pet) {
        cardCellBinding.petName.text = petForm.petname
        cardCellBinding.petAge.text = petForm.age.toString()
        cardCellBinding.petPhoto.setImageURI(petForm.petphoto)
    }
}