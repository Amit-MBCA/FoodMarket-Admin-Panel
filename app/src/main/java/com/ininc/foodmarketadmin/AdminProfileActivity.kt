package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ininc.foodmarketadmin.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.idnameetv.isEnabled=false
        binding.idaddressetv.isEnabled=false
        binding.idemailetv.isEnabled=false
        binding.idphoneetv.isEnabled=false
        binding.idpasswordetv.isEnabled=false

        var isEnable=false
        binding.ideditbtn.setOnClickListener {
            isEnable = !isEnable
            binding.idnameetv.isEnabled=isEnable
            binding.idaddressetv.isEnabled=isEnable
            binding.idemailetv.isEnabled=isEnable
            binding.idphoneetv.isEnabled=isEnable
            binding.idpasswordetv.isEnabled=isEnable
            if (isEnable){
                binding.idnameetv.requestFocus()
            }
        }
        binding.idbackbtn.setOnClickListener {
            finish()
        }
    }
}