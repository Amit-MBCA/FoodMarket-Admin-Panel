package com.ininc.foodmarketadmin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ininc.foodmarketadmin.adapter.MenuItemAdapter
import com.ininc.foodmarketadmin.databinding.ActivityAllItemBinding
import com.ininc.foodmarketadmin.model.AllMenu

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private var menuItems:ArrayList<AllMenu> = ArrayList()
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().reference
        retrieveMenuItem()
//        val menuFoodName=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
//        val menuItemPrice= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
//        val menuImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)



        binding.idbackbtn.setOnClickListener {
            finish()
        }

    }

    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")

        //fetch food data from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data before populating
                menuItems.clear()
                //loop for through each food item
                for(foodSnapShot in snapshot.children){
                    val menuItem=foodSnapShot.getValue(AllMenu::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter=MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference)
        binding.MenuRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter
    }
}