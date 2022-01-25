package com.example.holaandroidstudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class TodasLasPantallas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todas_las_pantallas)

        val botonYoutube: Button = findViewById(R.id.youtubeBtn)
        val botonPortal : Button = findViewById(R.id.portalBtn)

        botonYoutube.setOnClickListener(){
            startActivity(Intent(this,VideoYoutube::class.java))
        }

        botonPortal.setOnClickListener(){
            startActivity(Intent(this,PortalApp::class.java))
        }


    }

    fun irCarrusel(view: View) {
        startActivity(Intent(this,Carrusel::class.java))
    }
    fun irFormulario(view: View) {
        startActivity(Intent(this,Formulario::class.java))
    }
    /*fun irPortal(view: View) {
        startActivity(Intent(this,PortalApp::class.java))
    }
    fun irYoutube(view: View) {
        startActivity(Intent(this,VideoYoutube::class.java))
    }*/


}