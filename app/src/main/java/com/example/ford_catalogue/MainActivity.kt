package com.example.ford_catalogue

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()

            if (query.isEmpty()) {
                Toast.makeText(this, "Masukkan nama mobil Ford terlebih dahulu!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, DetailActivity::class.java)
                startActivity(intent)
            }
        }
    }
}