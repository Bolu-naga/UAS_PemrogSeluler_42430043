package com.example.ford_catalogue

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    private lateinit var tvCarName: TextView
    private lateinit var tvCarTagline: TextView
    private lateinit var tvCategoryBadge: TextView
    private lateinit var tvPriceValue: TextView
    private lateinit var tvEngineValue: TextView
    private lateinit var tvPowerValue: TextView
    private lateinit var tvTransmissionValue: TextView
    private lateinit var tvFuelValue: TextView
    private lateinit var tvSeatsValue: TextView
    private lateinit var tvSpecDescription: TextView
    private lateinit var tvCarHeroIcon: TextView
    private lateinit var tvHeroDetailText: TextView
    private lateinit var btnFavoriteDetail: TextView
    private lateinit var btnGallery: Button
    private lateinit var btnCompare: Button
    private lateinit var btnBack: Button

    private var isFavorite = false
    private var carName = "Ford Car"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViews()
        loadCarData()
        setupActions()
    }

    private fun initViews() {
        tvCarName = findViewById(R.id.tvCarName)
        tvCarTagline = findViewById(R.id.tvCarTagline)
        tvCategoryBadge = findViewById(R.id.tvCategoryBadge)
        tvPriceValue = findViewById(R.id.tvPriceValue)
        tvEngineValue = findViewById(R.id.tvEngineValue)
        tvPowerValue = findViewById(R.id.tvPowerValue)
        tvTransmissionValue = findViewById(R.id.tvTransmissionValue)
        tvFuelValue = findViewById(R.id.tvFuelValue)
        tvSeatsValue = findViewById(R.id.tvSeatsValue)
        tvSpecDescription = findViewById(R.id.tvSpecDescription)
        tvCarHeroIcon = findViewById(R.id.tvCarHeroIcon)
        tvHeroDetailText = findViewById(R.id.tvHeroDetailText)
        btnFavoriteDetail = findViewById(R.id.btnFavoriteDetail)
        btnGallery = findViewById(R.id.btnGallery)
        btnCompare = findViewById(R.id.btnCompare)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun loadCarData() {
        carName = intent.getStringExtra("CAR_NAME") ?: "Ford Car"

        val carCategory = intent.getStringExtra("CAR_CATEGORY") ?: "Unknown"
        val carEngine = intent.getStringExtra("CAR_ENGINE") ?: "Unknown Engine"
        val carPower = intent.getStringExtra("CAR_POWER") ?: "Unknown Power"
        val carPrice = intent.getStringExtra("CAR_PRICE") ?: "Unknown Price"
        val carTagline = intent.getStringExtra("CAR_TAGLINE") ?: "No tagline available."
        val carTransmission = intent.getStringExtra("CAR_TRANSMISSION") ?: "Unknown Transmission"
        val carFuel = intent.getStringExtra("CAR_FUEL") ?: "Unknown Fuel"
        val carSeats = intent.getStringExtra("CAR_SEATS") ?: "Unknown Seats"

        tvCarName.text = carName
        tvCarTagline.text = carTagline
        tvCategoryBadge.text = carCategory.uppercase()
        tvPriceValue.text = carPrice
        tvEngineValue.text = carEngine
        tvPowerValue.text = carPower
        tvTransmissionValue.text = simplifyTransmission(carTransmission)
        tvFuelValue.text = carFuel
        tvSeatsValue.text = carSeats

        tvCarHeroIcon.text = getHeroText(carName, carCategory)
        tvHeroDetailText.text = getHeroSubtitle(carCategory)

        tvSpecDescription.text = buildGarageNote(
            name = carName,
            category = carCategory,
            engine = carEngine,
            power = carPower,
            fuel = carFuel
        )
    }

    private fun setupActions() {
        btnFavoriteDetail.setOnClickListener {
            isFavorite = !isFavorite

            if (isFavorite) {
                btnFavoriteDetail.text = "♥"
                btnFavoriteDetail.setTextColor(
                    ContextCompat.getColor(this, R.color.ford_red)
                )
                Toast.makeText(this, "$carName masuk ke garage favorit", Toast.LENGTH_SHORT).show()
            } else {
                btnFavoriteDetail.text = "♡"
                btnFavoriteDetail.setTextColor(
                    ContextCompat.getColor(this, R.color.ford_text_soft)
                )
                Toast.makeText(this, "$carName dihapus dari favorit", Toast.LENGTH_SHORT).show()
            }
        }

        btnGallery.setOnClickListener {
            Toast.makeText(this, "Gallery $carName segera hadir", Toast.LENGTH_SHORT).show()
        }

        btnCompare.setOnClickListener {
            Toast.makeText(this, "$carName siap dibandingkan di sprint berikutnya", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun simplifyTransmission(transmission: String): String {
        return transmission
            .replace("Automatic", "AT")
            .replace("Manual", "MT")
            .replace("Dual Clutch", "DCT")
    }

    private fun getHeroText(name: String, category: String): String {
        return when {
            name.contains("GT40", ignoreCase = true) -> "GT40"
            name.contains("Mustang", ignoreCase = true) -> "GT"
            name.contains("Raptor", ignoreCase = true) -> "RPT"
            name.contains("Bronco", ignoreCase = true) -> "BRN"
            name.contains("F-150", ignoreCase = true) -> "F150"
            category.equals("Electric", ignoreCase = true) -> "EV"
            category.equals("Legend", ignoreCase = true) -> "LEG"
            category.equals("Supercar", ignoreCase = true) -> "GT"
            category.equals("SUV", ignoreCase = true) -> "SUV"
            category.equals("Truck", ignoreCase = true) -> "TRK"
            category.equals("Classic", ignoreCase = true) -> "CLS"
            else -> "FORD"
        }
    }

    private fun getHeroSubtitle(category: String): String {
        return when (category.lowercase()) {
            "legend" -> "Le Mans Heritage Machine"
            "supercar" -> "Modern Racing DNA"
            "sport" -> "Performance Street Beast"
            "suv" -> "Adventure Ready SUV"
            "truck" -> "Powerful Utility Machine"
            "electric" -> "Future Electric Performance"
            "hybrid" -> "Smart Efficient Power"
            "sedan" -> "Comfort Driven Cruiser"
            "van" -> "Practical Road Companion"
            "classic" -> "Timeless American Icon"
            else -> "Digital Showroom View"
        }
    }

    private fun buildGarageNote(
        name: String,
        category: String,
        engine: String,
        power: String,
        fuel: String
    ): String {
        return "$name adalah koleksi Ford kategori $category dengan mesin $engine, tenaga $power, dan bahan bakar $fuel. Mobil ini cocok untuk pengguna yang ingin merasakan karakter Ford yang kuat, berani, dan berbeda dari katalog mobil biasa."
    }
}