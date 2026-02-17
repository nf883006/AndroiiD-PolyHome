package com.farikou.polyhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HousesAdapter(
    private val houses: List<House>, // Utilise maintenant la classe House globale
    private val onHouseClick: (House) -> Unit
) : RecyclerView.Adapter<HousesAdapter.HouseViewHolder>() {

    class HouseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.txtHouseId)
        //val txtOwner: TextView = view.findViewById(R.id.txtOwner)
        val btnEnter: Button = view.findViewById(R.id.btnEnter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = houses[position]
        holder.txtId.text = "Maison n°${house.houseId}"
        //holder.txtOwner.text = if (house.owner) "Propriétaire" else "Invité"

        holder.btnEnter.setOnClickListener { onHouseClick(house) }
    }

    override fun getItemCount() = houses.size
}
