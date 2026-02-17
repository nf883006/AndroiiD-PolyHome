package com.farikou.polyhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DevicesAdapter(
    private val devices: List<Device>,
    private val onCommandClick: (Device, String) -> Unit
) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtDeviceName)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
        val containerButtons: ViewGroup = view.findViewById(R.id.containerButtons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.txtName.text = "${device.type} (${device.id})"

        val status = when {
            device.opening != null -> "Ouverture : ${device.opening}%"
            device.power != null -> if (device.power > 0) "ALLUMÉ" else "ÉTEINT"
            else -> "Connecté"
        }
        holder.txtStatus.text = status

        holder.containerButtons.removeAllViews()
        device.availableCommands.forEach { command ->
            val btn = Button(holder.itemView.context)
            btn.text = command
            btn.setOnClickListener { onCommandClick(device, command) }
            holder.containerButtons.addView(btn)
        }
    }

    override fun getItemCount() = devices.size
}
