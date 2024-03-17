package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ininc.foodmarketadmin.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private val binding:ActivityCreateUserBinding by lazy{
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.idbackbtn.setOnClickListener {
            finish()
        }
    }
}