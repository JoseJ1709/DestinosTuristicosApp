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
        val destinos = loadDestinosFromJSON()
        val categoria = intent.getStringExtra("categoria")
        val destinosFiltrados = if(categoria == "Todos") {
            convertJSONArrayToList(destinos)
        } else {
            filtrarDestinosPorCategoria(destinos, categoria) }
        val nombresDestinos = mutableListOf<String>()
        for (destino in destinosFiltrados) { nombresDestinos.add(destino.getString("nombre")) }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresDestinos)
        lista.adapter = adapter
        lista.setOnItemClickListener { parent, view, position, id ->
            val destinoSeleccionado = destinosFiltrados[position]
            val intent = Intent(this, DetalleDestinoActivity::class.java)
            intent.putExtra("destino", destinoSeleccionado.toString())
            startActivity(intent) }
    }
    private fun loadDestinosFromJSON(): JSONArray {
        var json: String? = null
        try {
            val `is` = assets.open("destinos.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        val jsonObject = JSONObject(json)
        return jsonObject.getJSONArray("destinos")
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