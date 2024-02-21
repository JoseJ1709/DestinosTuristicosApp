package com.example.destinosturisticosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ExplorarActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar)
        recyclerView = findViewById(R.id.recycler_view_destinos)
        val categoria = intent.getStringExtra("categoria")

    }
}