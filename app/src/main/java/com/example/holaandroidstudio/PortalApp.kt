package com.example.holaandroidstudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_portal_app.*
import kotlinx.android.synthetic.main.pantalla2.*

class PortalApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portal_app)

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) para poner la flecha de retorno arriba a la izquierda
        supportActionBar!!.setTitle("Portal")

        // pasamos el bundle con los datos

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("claveEmail")
        val password: String? = bundle?.getString("claveNombre")

        pasoDatos(email ?: "", password ?: "")


    }

    private fun pasoDatos(email: String, Nombre: String) {

        emailTexto.text = email
        nombreTexto.text = Nombre

    }

    //Creamos el menu de 3 puntos que se asocia con un xml de tipo menú previamente creado
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_puntos_portal, menu)

        return true
    }

    // esta funcion es para darle funcionalidad a los items del menu de puntos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val seleccion_opcion: Int = item.itemId



        if (seleccion_opcion == R.id.opcioncarrusel) {
            irCarrusel()
        }
        if (seleccion_opcion == R.id.opcionYoutube){
            irYoutube()
        }
        if(seleccion_opcion == R.id.opcionCerrarSesion){
            FirebaseAuth.getInstance().signOut()
            //this.finish()
            startActivity(Intent(this, MainActivity::class.java))
        }


        return super.onOptionsItemSelected(item)
    }

    private fun irCarrusel() {
        startActivity(Intent(this,Carrusel::class.java))
    }

    private fun irYoutube(){
        startActivity(Intent(this,VideoYoutube::class.java))
    }

}