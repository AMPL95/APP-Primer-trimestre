package com.example.holaandroidstudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_formulario.*

class Formulario : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        val textoEnlace : TextView = findViewById(R.id.hipervinculo)
        textoEnlace.movementMethod = LinkMovementMethod.getInstance()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Registro de Usuario")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun registrar(view: View) {
        //obtenemos los campos de texto y los guardamos en objetos
        val email: EditText = findViewById(R.id.EmailRegistro)
        val passw: EditText = findViewById(R.id.contraseñaRegistro)
        val nombreUsuario : EditText = findViewById(R.id.Nombre)
        val apellidoUsuario : EditText = findViewById(R.id.Apellidos)


        var nombre = nombreUsuario.text.toString()
        var apellido = apellidoUsuario.text.toString()
        var emailString = email.text.toString()
        var passwordString = passw.text.toString()

        if (emailString != null && passwordString != null && nombre!=null && apellido != null ) {
            if (emailString != "" && passwordString != "" && nombre != "" && apellido != "") {
                if(switch1.isChecked) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                db.collection("users").document(nombreUsuario.text.toString()).set(
                                    hashMapOf(
                                        "Nombre" to nombreUsuario.text.toString(),
                                        "Apellido" to apellidoUsuario.text.toString(),
                                        "Contraseña" to passwordString,
                                        "Email" to emailString
                                    )
                                )
                                Toast.makeText(
                                    this,
                                    "Usuario Registrado con exito",
                                    Toast.LENGTH_SHORT
                                ).show()
                                irPortal(emailString,nombre)
                                //irPantallados(emailString, passwordString)
                            } else {
                                //ERROR
                                showError2()

                            }
                        }
                }else {
                    //aqui vendria el else del switch de las condiciones
                    showError3()
                }
            }else {
                // los campos no estas rellenos
                showError()
            }
        }
    }

    fun irPortal(email: String, nombre: String) {

        val bundleDatos = Bundle()
        bundleDatos.putString("claveEmail", email)
        bundleDatos.putString("claveNombre", nombre)

        val acceso = Intent(this, PortalApp::class.java)
        acceso.putExtras(bundleDatos)
        startActivity(acceso)


    }

    private fun showError2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERRORCITO 2")
        builder.setMessage("Se ha producido un error con Firebase")
        builder.setPositiveButton("Aceptar", null)
        builder.setIcon(R.drawable.icono_alerta)
        val dialog: AlertDialog = builder.create()
        dialog.show()
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

    private fun showError3() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Debes aceptar las condiciones de uso.")
        builder.setPositiveButton("Aceptar", null)
        builder.setIcon(R.drawable.icono_alerta)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}



