package com.ininc.foodmarketadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ininc.foodmarketadmin.databinding.ActivityAdminProfileBinding
import com.ininc.foodmarketadmin.model.UserModel

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        adminReference=database.reference.child("user")
        binding.idnameetv.isEnabled=false
        binding.idaddressetv.isEnabled=false
        binding.idemailetv.isEnabled=false
        binding.idphoneetv.isEnabled=false
        binding.idpasswordetv.isEnabled=false
        binding.saveinfobtn.isEnabled=false
        var isEnable=false
        retrieveUserData()
        binding.ideditbtn.setOnClickListener {
            isEnable = !isEnable
            binding.idnameetv.isEnabled=isEnable
            binding.idaddressetv.isEnabled=isEnable
//            binding.idemailetv.isEnabled=isEnable
            binding.idphoneetv.isEnabled=isEnable
            binding.idpasswordetv.isEnabled=isEnable
            binding.saveinfobtn.isEnabled=isEnable
            if (isEnable){
                binding.idnameetv.requestFocus()
            }
        }

        binding.idbackbtn.setOnClickListener {
            finish()
        }
        binding.saveinfobtn.setOnClickListener {
            updateUserData()
        }
    }



    private fun retrieveUserData() {
        val currentUserUid=auth.currentUser?.uid
        if(currentUserUid!=null){
            val userReference=adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var ownerName=snapshot.child("name").getValue()
                        var ownerAddress=snapshot.child("address").getValue()
                        var ownerEmail=snapshot.child("email").getValue()
                        var ownerPhone=snapshot.child("phone").getValue()
                        var ownerPassword=snapshot.child("password").getValue()
                        setDataToTextView(ownerName,ownerEmail,ownerPassword,ownerAddress,ownerPhone)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        ownerEmail: Any?,
        ownerPassword: Any?,
        ownerAddress: Any?,
        ownerPhone: Any?
    ) {
        binding.idnameetv.setText(ownerName.toString())
        binding.idemailetv.setText(ownerEmail.toString())
        binding.idphoneetv.setText(ownerPhone.toString())
        binding.idaddressetv.setText(ownerAddress.toString())
        binding.idpasswordetv.setText(ownerPassword.toString())
    }
    private fun updateUserData() {
        var updateName=binding.idnameetv.text.toString()
        var updateEmail=binding.idemailetv.text.toString()
        var updatePhone=binding.idphoneetv.text.toString()
        var updateAddress=binding.idaddressetv.text.toString()
        var updatePassword=binding.idpasswordetv.text.toString()
        val currentUserUid=auth.currentUser?.uid
        if(currentUserUid != null){
            val userReference=adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)

            Toast.makeText(this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
            auth.currentUser?.updatePassword(updatePassword)

        }
        else{
            Toast.makeText(this,"Profile Updation Failed",Toast.LENGTH_SHORT).show()
        }
    }
}