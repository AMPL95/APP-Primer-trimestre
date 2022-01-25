package com.example.holaandroidstudio
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.pantalla2.*


class Pantalla2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla2)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("claveEmail")
        val password: String? = bundle?.getString("claveContraseña")

        setup(email ?: "", password ?: "")
    }

    private fun setup(email: String, password: String) {
        title = "Inicio"

        emailTextView.text = email
        contraseñaTextView.text = password

        cerrarSesion.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}
