package com.ininc.foodmarketadmin

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
//import com.android.volley.Request
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.ininc.foodmarketadmin.databinding.ActivitySignUpBinding
import com.ininc.foodmarketadmin.model.UserModel
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var userName:String
    private lateinit var address:String
    private lateinit var shopName:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private var citiesUrl:String="https://amit-mbca.github.io/FoodMarket-Admin-Panel/cities.json"
    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth=Firebase.auth
        //initialize firebase database
        database=Firebase.database.reference
        val locationList= arrayOf("Jind","Kaithal","Karnal","Sonipat","Hisar")
        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView=binding.idlistoflocation
        autoCompleteTextView.setAdapter(adapter)
        binding.idlistoflocation.onItemClickListener{

        }
//        getCitiesNames()
        binding.idcreateaccountbtn.setOnClickListener{
            email=binding.idemailorphone.text.toString().trim()
            userName=binding.idnameofowner.text.toString()
            shopName=binding.idnameofresto.text.toString().trim()
            password=binding.idsignuppassword.text.toString().trim()

            if(userName.isBlank() || email.isBlank() || shopName.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }
        binding.alreadyaccountbtn.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCitiesNames() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://amit-mbca.github.io/FoodMarket-Admin-Panel/cities.json"

        val stringRequest = StringRequest(
            Request.Method.GET, citiesUrl,
            { response ->
                var responeObj=JSONObject(response)
                responeObj.get("name")
                Log.d("Cities","Cities Name: $responeObj")
            },
            { error ->

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }

    //Save data into database
    private fun saveUserData() {
        email=binding.idemailorphone.text.toString().trim()
        userName=binding.idnameofowner.text.toString().trim()
        shopName=binding.idnameofresto.text.toString().trim()
        password=binding.idsignuppassword.text.toString().trim()
        val user=UserModel(userName,shopName,address,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase database
        database.child("user").child("seller").child(userId).setValue(user)
    }
    private fun AutoCompleteTextView.onItemClickListener(value: () -> Unit) {

    }

}

