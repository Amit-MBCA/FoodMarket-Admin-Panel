package com.ininc.foodmarketadmin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ininc.foodmarketadmin.databinding.ActivityAddItemBinding
import com.ininc.foodmarketadmin.model.AllMenu

class AddItemActivity : AppCompatActivity() {

    //Food Item Details
    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private var foodImageUri:Uri ?= null
    private lateinit var foodDesc:String
    private lateinit var foodIngred:String

    //Firebase
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        binding.additembtn.setOnClickListener {
            //Get Data from Fields
            foodName=binding.identerfoodname.text.toString().trim()
            foodPrice=binding.identerfoodprice.text.toString().trim()
            foodDesc=binding.iddescet.text.toString().trim()
            foodIngred=binding.idingredientstv.text.toString().trim()
            if(!(foodName.isBlank()||foodPrice.isBlank()||foodDesc.isBlank()||foodIngred.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }


        }
        binding.idselectimage.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.idbackbtn.setOnClickListener {
//            val intent= Intent(this,MainActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }

    private fun uploadData() {
        //Get a reference to the "menu" node in the database
        val menuRef=database.getReference("menu")
        //Generate a unique key for the new menu
        val newItemKey=menuRef.push().key

        if(foodImageUri!=null){
            val storageRef=FirebaseStorage.getInstance().reference
            val imageRef=storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask=imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
                    //Create a new menu item
                    val newItem=AllMenu(
                        newItemKey,
                        foodName=foodName,
                        foodPrice=foodPrice,
                        foodDesc=foodDesc,
                        foodIngred=foodIngred,
                        foodImage=downloadUrl.toString()
                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"Data Uploaded Failure",Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }
                .addOnFailureListener {
                    Toast.makeText(this,"Please Select Any Image",Toast.LENGTH_SHORT).show()
                }
        }
            else {
                Toast.makeText(this,"Image Upload Failed",Toast.LENGTH_SHORT).show()
            }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.idselectedimg.setImageURI(uri)
            foodImageUri=uri
        }

    }
}