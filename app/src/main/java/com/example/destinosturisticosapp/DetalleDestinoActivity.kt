package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleDestinoActivity : AppCompatActivity() {

    private lateinit var quitarButton: Button
    private lateinit var agregarButton: Button
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
        agregarButton = findViewById<Button>(R.id.agregarButton)
        quitarButton = findViewById<Button>(R.id.quitarButton)
        nombreTextView.text = destino.getString("nombre")
        paisTextView.text = destino.getString("pais")
        categoriaTextView.text = destino.getString("categoria")
        planTextView.text = destino.getString("plan")
        precioTextView.text = "USD " + destino.getString("precio")
        check()
        agregarButton.setOnClickListener(agregarButtonOnClickListener)
        quitarButton.setOnClickListener(quitarButtonOnClickListener)
        fetchWeatherData(destino.getString("nombre"))
    }
    private val agregarButtonOnClickListener = View.OnClickListener {
        MainActivity.addToFavoritos(destino)
        agregarButton.isEnabled = false
        quitarButton.isEnabled = true
        Toast.makeText(this, "Añadido a Favoritos", Toast.LENGTH_SHORT).show()
    }

    private val quitarButtonOnClickListener = View.OnClickListener {
        MainActivity.removeFromFavoritos(destino)
        quitarButton.isEnabled = false
        agregarButton.isEnabled = true
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
            quitarButton.isEnabled = true
            agregarButton.isEnabled = false
        } else {
            agregarButton.isEnabled = true
            quitarButton.isEnabled = false
        }
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()
            .create(ApiInterface::class.java)
        val response = retrofit.getWeatherData(cityName, "690a1a323163441e87b221704242602")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val temperature = responseBody?.current?.temp_c
                    updateTemperature(temperature)
                } else {
                    Log.e("API_RESPONSE", "Error fetching weather data: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                Log.e("API_RESPONSE", "Error fetching weather data", t)
            }
        })
    }
    private fun updateTemperature(temperature: Double?) {
        val txtViewTemperaturaC = findViewById<TextView>(R.id.Temperatura)
        txtViewTemperaturaC.text = temperature?.toString() + "ºC"
    }
}