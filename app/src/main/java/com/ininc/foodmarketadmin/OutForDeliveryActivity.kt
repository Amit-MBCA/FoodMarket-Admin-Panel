package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ininc.foodmarketadmin.adapter.DeliveryAdapter
import com.ininc.foodmarketadmin.databinding.ActivityOutForDeliveryBinding
import com.ininc.foodmarketadmin.model.OrderDetails

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private var completeOrderList:ArrayList<OrderDetails> = arrayListOf(

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val customerName= arrayListOf("Abhay","Vikas","Shivam","Shubham","Vinay")
//        val moneyStatus= arrayListOf("Received","Not Received","Pending","Not Received","Received")


        //retrieve and display completed order
        retrieveCompleteOrderDetails()
        binding.idbackbtn.setOnClickListener {
            finish()
        }
    }

    private fun retrieveCompleteOrderDetails() {
        database=FirebaseDatabase.getInstance()
        val completeOrderReference=database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before populating it with new data
                completeOrderList.clear()

                for( orderSnapshot in snapshot.children){
                    val completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let{
                        completeOrderList.add(it)
                    }
                }
                //reverse the list to display latest order first
                completeOrderList.reverse()
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataIntoRecyclerView() {
        val customerName= mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()
        for(order in completeOrderList){
            order.userName?.let { customerName.add(it) }
            order.paymentReceived?.let { moneyStatus.add(it) }
        }
        val adapter=DeliveryAdapter(customerName,moneyStatus)
        binding.iddeliveryrv.layoutManager=LinearLayoutManager(this)
        binding.iddeliveryrv.adapter=adapter

    }
}