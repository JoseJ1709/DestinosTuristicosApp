package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import kotlin.random.Random

class RecomendacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendacion)
        val destinosStr = intent.getStringExtra("destinos")
        val destinos = JSONArray(destinosStr)
        val categoriaMasFrecuente = obtenerCategoriaMasFrecuente()
        val nombreTextView = findViewById<TextView>(R.id.nombre)
        val paisTextView = findViewById<TextView>(R.id.pais)
        val categoriaTextView = findViewById<TextView>(R.id.categoria)
        val planTextView = findViewById<TextView>(R.id.plan)
        val precioTextView = findViewById<TextView>(R.id.precio)
        mostrarRecomendacion(nombreTextView, paisTextView, categoriaTextView, planTextView, precioTextView, categoriaMasFrecuente, destinos)

    }
    private fun mostrarRecomendacion(nombreTextView: TextView, paisTextView: TextView, categoriaTextView: TextView, planTextView: TextView, precioTextView: TextView, categoriaMasFrecuente: String, destinos: JSONArray) {
        if (categoriaMasFrecuente == "NA") {
            nombreTextView.text = "NA"
        } else {
            val destinoAleatorio = obtenerDestinoAleatorio(categoriaMasFrecuente)
            if (destinoAleatorio != null) {
                nombreTextView.text = destinoAleatorio.getString("nombre")
                paisTextView.text = destinoAleatorio.getString("pais")
                categoriaTextView.text = destinoAleatorio.getString("categoria")
                planTextView.text = destinoAleatorio.getString("plan")
                precioTextView.text = "USD " + destinoAleatorio.getString("precio")
            } else {
                nombreTextView.text = "NA"
            }
        }
    }
    fun obtenerCategoriaMasFrecuente(): String {
        if (MainActivity.favoritosList.isEmpty()) {
            return "NA"
        }
        val categoriasContadas = mutableMapOf<String, Int>()
        for (destino in MainActivity.favoritosList) {
            val categoria = destino.getString("categoria")
            val count = categoriasContadas.getOrDefault(categoria, 0)
            categoriasContadas[categoria] = count + 1
        }
        val categoriaMasFrecuente = categoriasContadas.maxByOrNull { it.value }!!.key
        return categoriaMasFrecuente
    }
    fun obtenerDestinoAleatorio(categoria: String): JSONObject? {
        val destinosEnCategoria = mutableListOf<JSONObject>()
        for (destino in MainActivity.favoritosList) {
            if (destino.getString("categoria") == categoria) {
                destinosEnCategoria.add(destino)
            }
        }
        if (destinosEnCategoria.isEmpty()) {
            return null
        }
        return destinosEnCategoria[Random.nextInt(destinosEnCategoria.size)]
    }
}
