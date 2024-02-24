package com.example.destinosturisticosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class ExplorarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar)
        val lista = findViewById<ListView>(R.id.lista)
        val extras = intent.extras
        val categoria = extras?.getString("categoria")
        val destinosStr = extras?.getString("destinos")
        val destinos = JSONArray(destinosStr)
        val destinosFiltrados = if(categoria == "Todos") {
            convertJSONArrayToList(destinos)
        } else {
            filtrarDestinosPorCategoria(destinos, categoria) }
        val nombresDestinos = mutableListOf<String>()
        for (destino in destinosFiltrados) { nombresDestinos.add(destino.getString("nombre")) }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresDestinos)
        lista.adapter = adapter
        setupItemClickListener(lista, destinosFiltrados)
    }
    private fun setupItemClickListener(lista: ListView, destinosFiltrados: List<JSONObject>) {
        lista.setOnItemClickListener { parent, view, position, id ->
            val destinoSeleccionado = destinosFiltrados[position]
            val intent = Intent(this, DetalleDestinoActivity::class.java)
            intent.putExtra("destino", destinoSeleccionado.toString())
            startActivity(intent)
        }
    }
    private fun filtrarDestinosPorCategoria(destinos: JSONArray, categoria: String?): List<JSONObject> {
        val destinosFiltrados = mutableListOf<JSONObject>()
        for (i in 0 until destinos.length()) {
            try {
                val destino = destinos.getJSONObject(i)
                if (destino.getString("categoria") == categoria) {
                    destinosFiltrados.add(destino)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return destinosFiltrados
    }
    private fun convertJSONArrayToList(jsonArray: JSONArray): List<JSONObject> {
        val list = mutableListOf<JSONObject>()
        for (i in 0 until jsonArray.length()) {
            try {
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return list
    }
}