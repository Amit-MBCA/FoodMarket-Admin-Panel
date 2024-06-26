package com.ininc.foodmarketadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ininc.foodmarketadmin.databinding.PendingOrdersItemBinding
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class PendingOrderAdapter(private val customerNames:MutableList<String>,private val totalPrice:MutableList<String>,private val foodImages:MutableList<String>,private val context:Context,private val itemClicked:OnItemClicked):
    RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {
    interface OnItemClicked{
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemDispatchClickListener(position: Int)
//        abstract fun updateOrderAcceptStatus(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding=PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = customerNames.size
    inner class PendingOrderViewHolder(private val binding:PendingOrdersItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false

        fun bind(position: Int) {
            binding.apply {
                idcustomername.text=customerNames[position]
                iditemstotalpay.text=totalPrice[position]
//                idorderedfoodimage.setImageResource(foodImages[position])
                val imgString=foodImages[position]
                var uri= Uri.parse(imgString)
                Glide.with(context).load(uri).into(idorderedfoodimage)
                idorderacceptbtn.apply {
                    if((!isAccepted)){
                        text="Accept"
                    }else{
                        text="Dispatch"
                    }

                    setOnClickListener {
                        if(!isAccepted){
                            text="Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                            itemClicked.onItemAcceptClickListener(position)
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                            itemClicked.onItemDispatchClickListener(position)
                        }
                    }
                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }

        }
        private fun showToast(message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }

}