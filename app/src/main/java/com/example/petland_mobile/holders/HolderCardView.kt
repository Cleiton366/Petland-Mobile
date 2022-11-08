package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CellCardBinding
import com.example.petland_mobile.models.Pet

class HolderCardView (private val cardCellBinding: CellCardBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindPet(petForm : Pet) {

        cardCellBinding.petName.text = "Name: ${petForm.petname}"
        cardCellBinding.petAge.text = "Age: ${petForm.age}"
        cardCellBinding.petCity.text = "City:${petForm.city}"
        cardCellBinding.petMedicalCondition.text = "medical Condition: ${petForm.medicalcondition}"
        cardCellBinding.petPhoto.setImageURI(petForm.petphoto)
    }
}