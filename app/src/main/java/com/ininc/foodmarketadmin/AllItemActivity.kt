package com.ininc.foodmarketadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ininc.foodmarketadmin.adapter.AddItemAdapter
import com.ininc.foodmarketadmin.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val menuFoodName=listOf("Burger","Sandwich","Momos","Item","Sandwich","Momos")
        val menuItemPrice= listOf("$5","$4","$6","$5","$4","$6")
        val menuImages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
        val adapter=AddItemAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuImages))
        binding.MenuRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter

        binding.idbackbtn.setOnClickListener {
            finish()
        }

    }
}