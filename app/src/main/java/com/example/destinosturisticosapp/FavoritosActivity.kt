package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.destinosturisticosapp.MainActivity.Companion.favoritosList

class FavoritosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        val listaFavoritos = findViewById<ListView>(R.id.listaFavoritos)
        val nombresFavoritos = mutableListOf<String>()
        for (destino in favoritosList) { nombresFavoritos.add(destino.getString("nombre")) }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresFavoritos)
        listaFavoritos.adapter = adapter
    }
}