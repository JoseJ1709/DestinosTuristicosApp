package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject

class DetalleDestinoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)
        val jsonString = intent.getStringExtra("destino")
        val destino = JSONObject(jsonString)
        val nombreTextView = findViewById<TextView>(R.id.nombre)
        val paisTextView = findViewById<TextView>(R.id.pais)
        val categoriaTextView = findViewById<TextView>(R.id.categoria)
        val planTextView = findViewById<TextView>(R.id.plan)
        val precioTextView = findViewById<TextView>(R.id.precio)
        nombreTextView.text = destino.getString("nombre")
        paisTextView.text = destino.getString("pais")
        categoriaTextView.text = destino.getString("categoria")
        planTextView.text = destino.getString("plan")
        precioTextView.text = "USD " + destino.getString("precio")
    }
}