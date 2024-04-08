package com.ininc.foodmarketadmin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.ininc.foodmarketadmin.databinding.ActivityPhoneAuthenticationBinding
import java.util.concurrent.TimeUnit

class PhoneAuthentication : AppCompatActivity() {
    private val binding:ActivityPhoneAuthenticationBinding by lazy{
        ActivityPhoneAuthenticationBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String?=""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= Firebase.auth
//        binding.idtventer.visibility= View.INVISIBLE
//        binding.pinview.visibility= View.INVISIBLE
//        binding.verifyotp.visibility= View.INVISIBLE
        binding.idgetotpbtn.setOnClickListener {
            val mobNo=binding.idgetnumber.text.toString()
            startPhoneNumberVerification(mobNo)
        }
        binding.verifyotp.setOnClickListener {
            val otp = binding.pinview.text.toString()
            verifyPhoneNumberWithCode(storedVerificationId,otp)
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("Verification", "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredentialSent(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Verification", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("Verification", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential=PhoneAuthProvider.getCredential(verificationId!!,code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d("IsSuccessful","SignInWithLoginCredentials Success")
                    val user=task.result?.user
//                    Toast.makeText(this,"On intent mode", Toast.LENGTH_LONG).show()

                    val intent= Intent(this@PhoneAuthentication,MainActivity::class.java)
                    startActivity(intent)
                } else{
                    //signin failed and display a message and update the UI
                    Log.w("Failed","Failed")
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        //the verification code entered is invalid
                    }
                    //Update the UI
                }
            }

    }

    private fun signInWithPhoneAuthCredentialSent(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    binding.idtventer.visibility= View.VISIBLE
                    binding.pinview.visibility= View.VISIBLE
                    binding.verifyotp.visibility= View.VISIBLE
                    binding.idgetotpbtn.isEnabled=false
                    binding.idgetnumber.isEnabled=false
                    Toast.makeText(this,"OTP Sent",Toast.LENGTH_SHORT).show()

                    Log.d("IsSuccessful","SignInWithLoginCredentials Success")
                    val user=task.result?.user
//                    val intent=Intent(this@PhoneAuthentication,MainActivity::class.java)
//                    startActivity(intent)
                } else{
                    //signin failed and display a message and update the UI
                    Log.w("Failed","Failed")
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        //the verification code entered is invalid
                    }
                    //Update the UI
                }
            }
    }

    private fun startPhoneNumberVerification(mobNo: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobNo) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}