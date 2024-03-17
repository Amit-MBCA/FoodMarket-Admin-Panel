package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ininc.foodmarketadmin.adapter.DeliveryAdapter
import com.ininc.foodmarketadmin.adapter.PendingOrderAdapter
import com.ininc.foodmarketadmin.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding:ActivityPendingOrderBinding  by lazy{
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val customerName= arrayListOf("Abhay","Vikas","Shivam","Shubham","Vinay")
        val ordersQuantity= arrayListOf("4","3","1","5","2")
        val orderedFoodImages= arrayListOf(R.drawable.croppedcirclelogo,R.drawable.croppedcirclelogo,R.drawable.croppedcirclelogo,R.drawable.croppedcirclelogo,R.drawable.croppedcirclelogo)
        val adapter= PendingOrderAdapter(customerName,ordersQuantity,orderedFoodImages,this)
        binding.idpendingorderrv.layoutManager= LinearLayoutManager(this)
        binding.idpendingorderrv.adapter=adapter
        binding.idbackbtn.setOnClickListener {
            finish()
        }

    }
}