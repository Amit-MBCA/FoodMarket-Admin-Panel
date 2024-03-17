package com.ininc.foodmarketadmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ininc.foodmarketadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
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


    }
}