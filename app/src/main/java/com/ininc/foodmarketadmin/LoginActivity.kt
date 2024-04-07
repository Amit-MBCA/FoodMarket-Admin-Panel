package com.ininc.foodmarketadmin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.ininc.foodmarketadmin.databinding.ActivityLoginBinding
import com.ininc.foodmarketadmin.model.UserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private var userName:String ?= null
    private var restoName:String ?= null
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInClient:GoogleSignInClient


    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= Firebase.auth
        database=Firebase.database.reference

        //initialize google sign in
        val googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id)).requestEmail().build()
        googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions)
        binding.donothavebutton.setOnClickListener{

            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            email=binding.idemail.text.toString().trim()
            password=binding.idpassetv.text.toString().trim()

            if(email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please fill the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                createUserAccount(email,password)
            }

        }
        binding.idgooglebtn.setOnClickListener {
            val signInIntent=googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
        binding.idphonebtn.setOnClickListener {
            val intent=Intent(this,PhoneAuthentication::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                val user: FirebaseUser? =auth.currentUser
                updateUI(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Create User & Login Successful",Toast.LENGTH_SHORT).show()
                        val user: FirebaseUser?=auth.currentUser
                        saveUserData()
                        updateUI(user)
                    }
                    else{
                        Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","AccountCreation: Failure",task.exception)
                    }

                }
            }
        }
    }

    private fun saveUserData() {
        email=binding.idemail.text.toString().trim()
        password=binding.idpassetv.text.toString().trim()
        val user=UserModel(userName,restoName,email,password)
        val userId:String?=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }


    //Launcher for Google Sign-in
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account:GoogleSignInAccount=task.result
                val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    authTask ->
                    if(authTask.isSuccessful){
                        //Successfully Sign In With Google
                        Toast.makeText(this,"Login Successful with Google",Toast.LENGTH_SHORT).show()
                        updateUI(authTask.result?.user)
//                        finish()
                    }
                    else{
                        Toast.makeText(this,"Google Sign-in Failed",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else{
                Toast.makeText(this,"Google Sign-in Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
    //Check if user if already logged in
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
    private fun updateUI(user:FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}