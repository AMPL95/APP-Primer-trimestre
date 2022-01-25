package com.example.holaandroidstudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoYoutube : AppCompatActivity() {

    lateinit var video: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoyoutube)

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) para poner la flecha de retorno arriba a la izquierda
        supportActionBar!!.setTitle("Video de youtube")

        video = findViewById(R.id.videoYoutube)
        lifecycle.addObserver(video)
       /* if (video.performClick()){
            supportActionBar!!.hide()
        }*/

    }

    //Creamos el menu de 3 puntos que se asocia con un xml de tipo menú previamente creado
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.menu_puntos_youtube, menu)

        return true
    }

    // esta funcion es para darle funcionalidad a los items del menu de puntos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val seleccion_opcion: Int = item.itemId



        if (seleccion_opcion == R.id.opcionPortal) {
            irPortal()
        }
        if (seleccion_opcion == R.id.opcioncarrusel){
            irCarrusel()
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

    private fun irCarrusel(){
        startActivity(Intent(this,Carrusel::class.java))
    }

}