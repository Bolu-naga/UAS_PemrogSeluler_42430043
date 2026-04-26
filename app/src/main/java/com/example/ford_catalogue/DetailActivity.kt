package com.example.ford_catalogue

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var tvCarName: TextView
    private lateinit var tvCarSpec: TextView
    private lateinit var tvCarPricePreview: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvCarName = findViewById(R.id.tvCarName)
        tvCarSpec = findViewById(R.id.tvCarSpec)
        tvCarPricePreview = findViewById(R.id.tvCarPricePreview)
        btnBack = findViewById(R.id.btnBack)

        val carName = intent.getStringExtra("CAR_NAME") ?: "Ford Car"
        val carCategory = intent.getStringExtra("CAR_CATEGORY") ?: "Unknown Category"
        val carEngine = intent.getStringExtra("CAR_ENGINE") ?: "Unknown Engine"
        val carPower = intent.getStringExtra("CAR_POWER") ?: "Unknown Power"
        val carPrice = intent.getStringExtra("CAR_PRICE") ?: "Unknown Price"
        val carTagline = intent.getStringExtra("CAR_TAGLINE") ?: "No tagline available."
        val carTransmission = intent.getStringExtra("CAR_TRANSMISSION") ?: "Unknown Transmission"
        val carFuel = intent.getStringExtra("CAR_FUEL") ?: "Unknown Fuel"
        val carSeats = intent.getStringExtra("CAR_SEATS") ?: "Unknown Seats"

        tvCarName.text = carName

        tvCarSpec.text = """
            $carTagline
            
            Category: $carCategory
            Engine: $carEngine
            Power: $carPower
            Transmission: $carTransmission
            Fuel: $carFuel
            Seats: $carSeats
        """.trimIndent()

        tvCarPricePreview.text = carPrice

        btnBack.setOnClickListener {
            finish()
        }
    }
}