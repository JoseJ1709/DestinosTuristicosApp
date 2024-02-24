package com.example.destinosturisticosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import org.json.JSONObject
class MainActivity : AppCompatActivity() {

    private val categorias = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
    companion object {
        var favoritosList = mutableListOf<JSONObject>()

        fun addToFavoritos(destino: JSONObject) {
                favoritosList.add(destino)
        }

        fun removeFromFavoritos(destino: JSONObject) {
            val iterator = favoritosList.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item.getString("nombre") == destino.getString("nombre") &&
                    item.getString("pais") == destino.getString("pais") &&
                    item.getString("categoria") == destino.getString("categoria") &&
                    item.getString("plan") == destino.getString("plan") &&
                    item.getString("precio") == destino.getString("precio")
                ) {
                    iterator.remove()
                }
            }
        }

    }
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

    private fun Favoritos() {
        val intent = Intent(this, FavoritosActivity::class.java)
        startActivity(intent)
    }

    private fun Recomendaciones() {}

}