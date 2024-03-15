package com.ininc.foodmarketadmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ininc.foodmarketadmin.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.idselectimage.setOnClickListener {
            pickimage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.idbackbtn.setOnClickListener {
//            val intent= Intent(this,MainActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }

    val pickimage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.idselectedimg.setImageURI(uri)
        }

    }
}