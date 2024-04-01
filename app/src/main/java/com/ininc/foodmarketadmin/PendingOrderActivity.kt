package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ininc.foodmarketadmin.adapter.DeliveryAdapter
import com.ininc.foodmarketadmin.adapter.PendingOrderAdapter
import com.ininc.foodmarketadmin.databinding.ActivityPendingOrderBinding
import com.ininc.foodmarketadmin.model.OrderDetails

class PendingOrderActivity : AppCompatActivity() {
    private val binding:ActivityPendingOrderBinding  by lazy{
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice:MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder:MutableList<String> = mutableListOf()
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database= FirebaseDatabase.getInstance()
        databaseOrderDetails=database.reference.child("OrderDetails")

        getOrderDetails()
        binding.idbackbtn.setOnClickListener {
            finish()
        }

    }

    private fun getOrderDetails() {
        //retrieve order details from firebase
        databaseOrderDetails.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    val orderDetails=orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for(orderItem in listOfOrderItem){
            //add data to respective list for populating the recycler view
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty()  }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.idpendingorderrv.layoutManager=LinearLayoutManager(this)
        var adapter=PendingOrderAdapter(listOfName,listOfTotalPrice,listOfImageFirstFoodOrder,this)
        binding.idpendingorderrv.adapter=adapter
    }
}