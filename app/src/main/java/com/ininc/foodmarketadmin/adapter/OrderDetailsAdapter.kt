package com.ininc.foodmarketadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ininc.foodmarketadmin.databinding.OrderDetailItemBinding

class OrderDetailsAdapter(private var context: Context,
    private var foodName:ArrayList<String>,
    private var foodImage:ArrayList<String>,
    private var foodQuantities:ArrayList<Int>,
    private var foodPrice:ArrayList<String>
    ): RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsViewHolder {
        val binding=OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsAdapter.OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodName.size

    inner class OrderDetailsViewHolder(private val binding:OrderDetailItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                idfoodname.text=foodName[position]
                iditemquantity.text=foodQuantities[position].toString()
                val uriString=foodImage[position]
                val uri=Uri.parse(uriString)
                Glide.with(context).load(uri).into(idorderedfoodimage)
                idorderfoodprice.text=foodPrice[position]
            }
        }

    }
}