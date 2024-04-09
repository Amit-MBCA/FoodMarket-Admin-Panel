package com.ininc.foodmarketadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {
    private val binding:ActivityPendingOrderBinding  by lazy{
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice:MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder:MutableList<String> = mutableListOf()
    private var listOfOrderItem:ArrayList<OrderDetails> = arrayListOf()
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
        var adapter=PendingOrderAdapter(listOfName,listOfTotalPrice,listOfImageFirstFoodOrder,this,this)
        binding.idpendingorderrv.adapter=adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent=Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails= listOfOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)

    }

    override fun onItemAcceptClickListener(position: Int) {
        //handle item acceptance and update database
        val childItemPushKey=listOfOrderItem[position].itemPushKey
        val clickItemOrderReference=childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)

    }

    private fun updateOrderAcceptStatus(position: Int) {
        //Update order acceptance in user's BuyHistory and OrderDetails
        val userIdOfClickedItem=listOfOrderItem[position].userUid
        val pushKeyOfClickedItem=listOfOrderItem[position].itemPushKey
        val buyHistoryReference=database.reference.child("user").child("buyer").child(userIdOfClickedItem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
//        val buyHistoryReference=database.reference.child("user").child("buyer").child("BuyHistory").child(userIdOfClickedItem!!).child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
//        buyHistoryReference.child("AcceptedOrder").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)

    }

    override fun onItemDispatchClickListener(position: Int) {
        //handle item dispatch and update database
        val dispatchItemPushKey= listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference=database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteItemFromOrderDetails(dispatchItemPushKey)
            }


    }

    private fun deleteItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this,"Order is Dispatched",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Order is not Dispatched",Toast.LENGTH_SHORT).show()
            }
    }
}