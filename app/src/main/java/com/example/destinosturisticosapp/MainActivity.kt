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
    private lateinit var Categorias: Spinner
    private lateinit var Explorar: Button
    private lateinit var Favoritos: Button
    private lateinit var Recomendaciones: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Categorias = findViewById(R.id.categorias)
        Explorar = findViewById(R.id.explorar)
        Favoritos = findViewById(R.id.favoritos)
        Recomendaciones = findViewById(R.id.recomendaciones)
        val categorias = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Categorias.adapter = adapter
        Explorar.setOnClickListener { explorarDestinos() }
        Favoritos.setOnClickListener { Favoritos() }
        Recomendaciones.setOnClickListener { Recomendaciones() }
    }
    private fun explorarDestinos() {
        val categoriaSeleccionada = Categorias.selectedItem.toString()
        val intent = Intent(this, ExplorarActivity::class.java).apply {
            putExtra("categoria", categoriaSeleccionada)
        }
        startActivity(intent)
    }

    private fun Favoritos() {}

    private fun Recomendaciones() {}

}