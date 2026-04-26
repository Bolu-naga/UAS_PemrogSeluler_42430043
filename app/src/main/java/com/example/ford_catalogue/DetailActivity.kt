package com.example.ford_catalogue

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var btnBack: Button
    private lateinit var tvDetailTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btnBack = findViewById(R.id.btnBack)
        tvDetailTitle = findViewById(R.id.tvDetailTitle)

        val carName = intent.getStringExtra("CAR_NAME")

        if (carName != null) {
            tvDetailTitle.text = carName
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}