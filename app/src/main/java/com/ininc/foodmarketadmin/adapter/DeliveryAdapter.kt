package com.ininc.foodmarketadmin.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ininc.foodmarketadmin.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerNames:ArrayList<String>,private val moneyStatus:ArrayList<String>) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): DeliveryAdapter.DeliveryViewHolder {
        val binding=DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class DeliveryViewHolder(private val binding:DeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                idcusotmername.text=customerNames[position]
                idreceived.text=moneyStatus[position]
                val colorMap= mapOf(
                    "Received" to Color.GREEN,
                    "Not Received" to Color.RED,
                    "Pending" to Color.GRAY
                )
                idreceived.setTextColor(colorMap[moneyStatus[position]]?:Color.RED)
                idstatuscolor.backgroundTintList= ColorStateList.valueOf(colorMap[moneyStatus[position]]?:Color.RED)
            }

        }

    }
}