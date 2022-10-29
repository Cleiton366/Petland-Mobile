package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CellCardBinding
import com.example.petland_mobile.holders.HolderCardView
import com.example.petland_mobile.models.Pet

class AdapterCard (private val pets : List<Pet>)
    : RecyclerView.Adapter<HolderCardView>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCardView {
        val from = LayoutInflater.from(parent.context)
        val binding = CellCardBinding.inflate(from, parent, false)
        return HolderCardView(binding)
    }

    override fun onBindViewHolder(holder: HolderCardView, position: Int) {
        holder.bindPet(pets[position])
    }

    override fun getItemCount(): Int = pets.size
}