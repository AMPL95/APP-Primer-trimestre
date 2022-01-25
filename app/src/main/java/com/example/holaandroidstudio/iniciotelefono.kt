package com.example.holaandroidstudio

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.holaandroidstudio.databinding.ActivityIniciotelefonoBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class iniciotelefono : AppCompatActivity() {

    private lateinit var binding: ActivityIniciotelefonoBinding

    // si el codigo no se envia
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVerificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "MAIN_TAG"

    //Progress dialog
    private lateinit var progresSDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciotelefonoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.phoneLl.visibility = View.VISIBLE
        binding.codeLl.visibility = View.GONE

        //inicia autenticacion firebase
        firebaseAuth = FirebaseAuth.getInstance()

        progresSDialog = ProgressDialog(this)
        progresSDialog.setTitle("Por favor espere")
        progresSDialog.setCanceledOnTouchOutside(false)


        // verificaciones varias
        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            Log.d(TAG, "onVerificationCompleted: yuhuuuu")
            signInWithPhoneAuthCredential(phoneAuthCredential)

            }

            override fun onVerificationFailed(e: FirebaseException) {

                progresSDialog.dismiss()
                Log.d(TAG, "onVerificationFailed: ")
                Toast.makeText(this@iniciotelefono, "${e.message}",Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.d(TAG, "onCodeSent: $verificationId")
                mVerificationId = verificationId
                forceResendingToken = token
                progresSDialog.dismiss()

                Log.d(TAG, "onCodeSent: $verificationId")

                binding.phoneLl.visibility = View.GONE
                binding.codeLl.visibility = View.VISIBLE
                Toast.makeText(this@iniciotelefono, "Codigo verificación enviado",Toast.LENGTH_SHORT).show()
                binding.codeSentDescriptionTv.text = "Introduce el codigo de verificación que se ha enviado a ${binding.phoneEt.text.toString().trim()}"
            }
        }


        binding.phoneContinueBtn.setOnClickListener {
            val phone = binding.phoneEt.text.toString().trim()

            // validar el numero de telefono

            if (TextUtils.isEmpty(phone)){
                Toast.makeText(this@iniciotelefono, "Por favor, introduce el número de teléfono",Toast.LENGTH_SHORT).show()
            }else{
                startPhoneNumberVerification(phone)
            }
        }

        binding.resendCodeTv.setOnClickListener {
            val phone = binding.phoneEt.text.toString().trim()

            // validar el numero de telefono

            if (TextUtils.isEmpty(phone)){
                Toast.makeText(this@iniciotelefono, "Por favor, introduce el número de teléfono",Toast.LENGTH_SHORT).show()
            }else{
                resendVerificationCode(phone, forceResendingToken)
            }

        }

        binding.codeSubmitBtn.setOnClickListener {
        //introducir codigo de verificacion
            val code = binding.codeEt.text.toString().trim()
            if(TextUtils.isEmpty(code)){
                Toast.makeText(this@iniciotelefono, "Por favor, introduce el codigo de verificación",Toast.LENGTH_SHORT).show()

            }else{
                verifyPhoneNumberWithCode(mVerificationId,code)
            }

        }
    }


        private fun startPhoneNumberVerification(phone:String){
            Log.d(TAG, "startPhoneNumberVerification: $phone")
            progresSDialog.setMessage("Verificando teléfono...")
            progresSDialog.show()

            val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone)
                .setTimeout(60, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBacks!!)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    private fun resendVerificationCode(phone: String, token: PhoneAuthProvider.ForceResendingToken?){
        progresSDialog.setMessage("Reenviando mensaje")
        progresSDialog.show()

        Log.d(TAG, "resendVerificationCode: $phone")

        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBacks!!)
            .setForceResendingToken(token!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String){
        Log.d(TAG, "verifyPhoneNumberWithCode: $verificationId $code")
        progresSDialog.setMessage("Verificando codigo...")
        progresSDialog.show()
        val credential = PhoneAuthProvider.getCredential(verificationId!!,code)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d(TAG, "signInWithPhoneAuthCredential: ")
        progresSDialog.setMessage("Accediendo a la app")

        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
        // login exito
                progresSDialog.dismiss()
                val phone = firebaseAuth.currentUser!!.phoneNumber
                Toast.makeText(this, "Has accedido como $phone", Toast.LENGTH_LONG).show()

                    startActivity(Intent(this, PortalApp::class.java))

            }
            .addOnFailureListener { e ->
                progresSDialog.dismiss()
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }

    }


}