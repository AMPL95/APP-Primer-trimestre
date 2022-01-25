package com.example.holaandroidstudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class Carrusel : AppCompatActivity() {

    var arrayImagenes: ArrayList<Int> = ArrayList()
    var vistaCarrusel: CarouselView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrusel)

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) para poner la flecha de retorno arriba a la izquierda
        supportActionBar!!.setTitle("Carrusel")

        arrayImagenes.add(R.drawable.articuno)
        arrayImagenes.add(R.drawable.charizard)
        arrayImagenes.add(R.drawable.squirtle)
        arrayImagenes.add(R.drawable.pokeball)

        vistaCarrusel = findViewById(R.id.carouselView)

        vistaCarrusel!!.pageCount = arrayImagenes.size

        vistaCarrusel!!.setImageListener(imageListener)

    }

    var imageListener = ImageListener {position, imageView -> imageView.setImageResource(arrayImagenes[position]) }



    //Creamos el menu de 3 puntos que se asocia con un xml de tipo menú previamente creado
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.menu_puntos_carrusel, menu)

        return true
    }

    // esta funcion es para darle funcionalidad a los items del menu de puntos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val seleccion_opcion: Int = item.itemId



        if (seleccion_opcion == R.id.opcionPortal) {
            irPortal()
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

    private fun irPortal() {
        startActivity(Intent(this,PortalApp::class.java))
    }

    private fun irYoutube(){
        startActivity(Intent(this,VideoYoutube::class.java))
    }
}