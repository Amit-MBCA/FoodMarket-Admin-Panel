package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ininc.foodmarketadmin.adapter.DeliveryAdapter
import com.ininc.foodmarketadmin.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val customerName= arrayListOf("Abhay","Vikas","Shivam","Shubham","Vinay")
        val moneyStatus= arrayListOf("Received","Not Received","Pending","Not Received","Received")
        val adapter=DeliveryAdapter(customerName,moneyStatus)
        binding.iddeliveryrv.layoutManager=LinearLayoutManager(this)
        binding.iddeliveryrv.adapter=adapter

    }
}