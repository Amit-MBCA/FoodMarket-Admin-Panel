package com.ininc.foodmarketadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ininc.foodmarketadmin.databinding.MenuItemBinding

class AddItemAdapter(private val MenuItemName:ArrayList<String>, private val MenuItemPrice:ArrayList<String>, private val MenuItemImg:ArrayList<Int>):
    RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities=IntArray(MenuItemName.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size

    inner class AddItemViewHolder(private val binding:MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity=itemQuantities[position]
                iditemfoodname.text=MenuItemName[position]
                iditemfoodprice.text=MenuItemPrice[position]
                iditemfoodimg.setImageResource(MenuItemImg[position])
                idnumofitems.text=quantity.toString()
                iditemminusbtn.setOnClickListener {
                    decreaseQuantity(position)
                }
                iditemplusbtn.setOnClickListener {
                    increaseQuantity(position)
                }
                iditemdeletebtn.setOnClickListener {
                    deleteQuantity(position)
                }



            }
        }

        private fun deleteQuantity(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImg.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,MenuItemName.size)
        }

        private fun decreaseQuantity(position: Int) {
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.idnumofitems.text=itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.idnumofitems.text=itemQuantities[position].toString()
            }
        }

    }


}