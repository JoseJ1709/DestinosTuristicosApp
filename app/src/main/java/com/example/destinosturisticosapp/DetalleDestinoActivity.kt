package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

class DetalleDestinoActivity : AppCompatActivity() {

    private lateinit var favoritosButton: Button
    private lateinit var destino: JSONObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)
        val jsonString = intent.getStringExtra("destino")
        destino = JSONObject(jsonString)
        val nombreTextView = findViewById<TextView>(R.id.nombre)
        val paisTextView = findViewById<TextView>(R.id.pais)
        val categoriaTextView = findViewById<TextView>(R.id.categoria)
        val planTextView = findViewById<TextView>(R.id.plan)
        val precioTextView = findViewById<TextView>(R.id.precio)
        favoritosButton = findViewById<Button>(R.id.favoritosButton)
        nombreTextView.text = destino.getString("nombre")
        paisTextView.text = destino.getString("pais")
        categoriaTextView.text = destino.getString("categoria")
        planTextView.text = destino.getString("plan")
        precioTextView.text = "USD " + destino.getString("precio")
        check()
        CambiarButton()
    }
    private fun check() {
        val listaPrincipal = MainActivity.favoritosList
        var esta = false
        for (item in listaPrincipal) {
            if (item.getString("nombre") == destino.getString("nombre") &&
                item.getString("pais") == destino.getString("pais") &&
                item.getString("categoria") == destino.getString("categoria") &&
                item.getString("plan") == destino.getString("plan") &&
                item.getString("precio") == destino.getString("precio")
            ) {
                esta = true
                break
            }
        }
        if (esta) {
            favoritosButton.text = "Quitar Favoritos"
        } else {
            favoritosButton.text = "Añadir a Favoritos"
        }
    }

    private fun CambiarButton() {
        favoritosButton.setOnClickListener {
            if (favoritosButton.text == "Quitar Favoritos") {
                MainActivity.removeFromFavoritos(destino)
                favoritosButton.text = "Añadir a Favoritos"
            } else {
                MainActivity.addToFavoritos(destino)
                favoritosButton.text = "Quitar Favoritos"
                Toast.makeText(this, "Añadido a Favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }


}