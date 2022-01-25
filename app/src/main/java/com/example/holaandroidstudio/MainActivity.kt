package com.example.holaandroidstudio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    /*

     hay que crear un boton, donde habra que crear las variables y estas recuperarlas con y R.id.
     Luego hay que hacer una funcion con ese boton y escibrir el codigo siguiente para crear un user(en este caso)

     fun delBotonNuevo{
     db.collection("users").document(email.text.toString().set(hashMapOf("name" to name.text.toString(),
     "surname" to surname.text.toString(), "password to passw.text.toString()))

     ir pantalla()

     };

     name, surname y passw son variables previemente creadas para  recuperar los datos del formulario con R.id
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setTheme(R.style.Theme_HolaAndroidStudio)
        Thread.sleep(4000)
        setContentView(R.layout.activity_main)



        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Firebase tutorial")
        analytics.logEvent("InitScreen", bundle)

        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        val gClient = GoogleSignIn.getClient(this, googleConf)

        val registerButton: ImageButton = findViewById(R.id.imageButton);
        registerButton.setOnClickListener {
            gClient.signOut()
            val intent = gClient.getSignInIntent()
            startActivityForResult(intent, 100)
        }


        //login de google
        comprobarSesion()

    }

    private fun comprobarSesion() {
        val preferences =
            getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
        //recogemos el email
        val email = preferences.getString("email", null)
        if (email != null) {
            irRegistro()
        }
    }


    fun irPantallados(email: String, contraseña: String) {
        val bundleMail = Bundle()
        bundleMail.putString("claveEmail", email)
        bundleMail.putString("claveContraseña", contraseña)

        val accesoPantalla2 = Intent(this, Pantalla2::class.java)
        accesoPantalla2.putExtras(bundleMail)
        startActivity(accesoPantalla2)
    }

    fun acceder(view: View) {
        val email: EditText = findViewById(R.id.emailTextId)
        val passw: EditText = findViewById(R.id.PasswordTextId)

        var emailString = email.text.toString()
        var passwordString = passw.text.toString()


        if (emailString != null && passwordString != null) {
            if (emailString != "" && passwordString != "") {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            irPortal(emailString,passwordString)
                        } else {
                            //ERROR
                            showError2()

                        }
                    }
            }
        } else {
            // los campos no estas rellenos
            showError()
        }
    }


    private fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Debes rellenar todos los campos")
        builder.setPositiveButton("Aceptar", null)
        builder.setIcon(R.drawable.icono_alerta)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showError2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Se ha producido un error con Firebase")
        builder.setIcon(R.drawable.icono_alerta)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // Navegamos a la siguient pantalla
                                irPortal()
                            } else {
                                // Error
                            }
                        }
                }
            } catch (e: ApiException) {
                // Google Sign In failed
            }
        }
    }

    fun irPortal() {
        val usuario = Bundle()
        usuario.putString("claveEmail", emailTextId.text.toString())

        val acceso = Intent(this, PortalApp::class.java)
        startActivity(acceso)

    }

    fun irPortal(email: String, contraseña: String) {

        val bundleDatos = Bundle()
        bundleDatos.putString("claveEmail", email)
        bundleDatos.putString("claveContraseña", contraseña)

        val acceso = Intent(this, PortalApp::class.java)
        acceso.putExtras(bundleDatos)
        startActivity(acceso)


    }

    fun irRegistro(view: View) {
        val acceso = Intent(this, Formulario::class.java)
        startActivity(acceso)
    }

    fun irRegistro(){
        val acceso = Intent(this, Formulario::class.java)
        startActivity(acceso)
    }

    fun onClickTelefono(view: View) {
        val acceso = Intent(this, iniciotelefono::class.java)
        startActivity(acceso)
    }

    fun irTodas(view: View) {
        startActivity(Intent(this,TodasLasPantallas::class.java))
    }

}