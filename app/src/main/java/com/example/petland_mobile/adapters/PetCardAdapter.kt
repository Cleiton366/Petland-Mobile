package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardCellBinding
import com.example.petland_mobile.holders.PetCardViewHolder
import com.example.petland_mobile.models.Pet

class PetCardAdapter (private val pets : List<Pet>)
    : RecyclerView.Adapter<PetCardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return PetCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetCardViewHolder, position: Int) {
        holder.bindPet(pets[position])
    }

    override fun getItemCount(): Int = pets.size

}