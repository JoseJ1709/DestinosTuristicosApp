package com.example.destinosturisticosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.view.View

class MainActivity : AppCompatActivity() {

    private val categorias = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Categorias = findViewById<Spinner>(R.id.categorias)
        val Explorar = findViewById<Button>(R.id.explorar)
        val Favoritos = findViewById<Button>(R.id.favoritos)
        val Recomendaciones = findViewById<Button>(R.id.recomendaciones)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Categorias.adapter = adapter
        Explorar.setOnClickListener { explorarDestinos(Categorias) }
        Favoritos.setOnClickListener { Favoritos() }
        Recomendaciones.setOnClickListener { Recomendaciones() }
    }

    private fun explorarDestinos(Categorias:Spinner) {
        val categoriaSeleccionada = Categorias.selectedItem.toString()
        val intent = Intent(this, ExplorarActivity::class.java).apply {
            putExtra("categoria", categoriaSeleccionada)
        }
        startActivity(intent)
    }

    private fun Favoritos() {}

    private fun Recomendaciones() {}

}