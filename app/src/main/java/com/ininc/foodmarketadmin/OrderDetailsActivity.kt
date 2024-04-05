package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ininc.foodmarketadmin.adapter.OrderDetailsAdapter
import com.ininc.foodmarketadmin.adapter.PendingOrderAdapter
import com.ininc.foodmarketadmin.databinding.ActivityOrderDetailsBinding
import com.ininc.foodmarketadmin.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName:String?=null
    private var userAddress:String?=null
    private var userPhone:String?=null
    private var totalPrice:String?=null
    private var foodNames:ArrayList<String> = arrayListOf()
    private var foodImages:ArrayList<String> = arrayListOf()
    private var foodQuantities:ArrayList<Int> = arrayListOf()
    private var foodPrices:ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.idbackbtn.setOnClickListener {
            finish()
        }

        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val receivedOrderDetails=intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails.let {orderDetails ->
            userName=receivedOrderDetails.userName
            foodNames= receivedOrderDetails.foodNames as ArrayList<String>
            foodImages=receivedOrderDetails.foodImages as ArrayList<String>
            foodQuantities= receivedOrderDetails.foodQuantities as ArrayList<Int>
            userAddress=receivedOrderDetails.address
            userPhone=receivedOrderDetails.phoneNumber
            foodPrices=receivedOrderDetails.foodPrices as ArrayList<String>
            totalPrice=receivedOrderDetails.totalPrice
            setUserDetails()
            setAdapter()

        }
    }

    private fun setAdapter() {
        binding.orderdetailsrv.layoutManager=LinearLayoutManager(this)
        val adapter=OrderDetailsAdapter(this,foodNames,foodImages,foodQuantities,foodPrices)
        binding.orderdetailsrv.adapter=adapter
    }

    private fun setUserDetails() {
        binding.idname.text=userName
        binding.idaddress.text=userAddress
        binding.idmobileno.text=userPhone
        binding.idtotalPay.text=totalPrice
    }
}