package com.ininc.foodmarketadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.ininc.foodmarketadmin.databinding.MenuItemBinding
import com.ininc.foodmarketadmin.model.AllMenu

//    private val menuList:ArrayList<String>, private val MenuItemPrice:ArrayList<String>, private val MenuItemImg:ArrayList<Int>
class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    private val databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position: Int) -> Unit
):
    RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities=IntArray(menuList.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AddItemViewHolder(private val binding:MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity=itemQuantities[position]
                val menuItem=menuList[position]
                val uriString=menuItem.foodImage
                val uri= Uri.parse(uriString)
                iditemfoodname.text=menuItem.foodName
                iditemfoodprice.text=menuItem.foodPrice
                Glide.with(context).load(uri).into(iditemfoodimg)
//                iditemfoodimg.setImageResource(menuList[position])
                idnumofitems.text=quantity.toString()
                iditemminusbtn.setOnClickListener {
                    decreaseQuantity(position)
                }
                iditemplusbtn.setOnClickListener {
                    increaseQuantity(position)
                }
                iditemdeletebtn.setOnClickListener {
//                    deleteQuantity(position)
                    onDeleteClickListener(position)
                }



            }
        }

        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
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