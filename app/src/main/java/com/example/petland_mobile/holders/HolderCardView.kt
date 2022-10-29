package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CellCardBinding
import com.example.petland_mobile.models.Pet

class HolderCardView (private val cardCellBinding: CellCardBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindPet(petForm : Pet) {

        cardCellBinding.petName.text = petForm.petname
        cardCellBinding.petAge.text = petForm.age.toString()
        cardCellBinding.petCity.text = petForm.city.toString()
        cardCellBinding.petMedicalCondition.text = petForm.medicalcondition.toString()
        cardCellBinding.petPhoto.setImageURI(petForm.petphoto)
    }
}