package com.ininc.foodmarketadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ininc.foodmarketadmin.databinding.ActivityMainBinding
import com.ininc.foodmarketadmin.model.OrderDetails

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var completedOrderReference: DatabaseReference
    private lateinit var pendingOrderReference: DatabaseReference
    private lateinit var wholeEarnReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.idadditemcv.setOnClickListener{
            val intent=Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.idallitemmenu.setOnClickListener {
            val intent=Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.idpendingorder.setOnClickListener {
            val intent=Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
        binding.iddispatchbtn.setOnClickListener {
            val intent=Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.idprofilebtn.setOnClickListener {
            val intent=Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.idcreateuser.setOnClickListener {
            val intent=Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }

        binding.idlogoutbtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        pendingOrders()
        completedOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listOfTotalPay= mutableListOf<Int>()
        wholeEarnReference=FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        wholeEarnReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    var completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
//                    completeOrder?.totalPrice?.replace("₹","")?.toIntOrNull()
                    completeOrder?.totalPrice?.toIntOrNull()
                        ?.let {i ->
                            listOfTotalPay.add(i)
                        }
                }

                binding.idwholetimeearn.text=listOfTotalPay.sum().toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun completedOrders() {
        completedOrderReference=database.reference.child("CompletedOrder")
        var completedOrderItemCount=0
        completedOrderReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount=snapshot.childrenCount.toInt()
                binding.idcompletedorders.text=completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun pendingOrders() {
        database=FirebaseDatabase.getInstance()
        pendingOrderReference=database.reference.child("OrderDetails")
        var pendingOrderItemCount=0
        pendingOrderReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount=snapshot.childrenCount.toInt()
                binding.idpendingorders.text=pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}