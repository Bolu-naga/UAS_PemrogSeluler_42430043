package com.example.ford_catalogue

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnSortAsc: Button
    private lateinit var btnSortDesc: Button
    private lateinit var lvCars: ListView

    private val allCars = FordCarRepository.getCars()
    private val displayedCars = ArrayList<FordCar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnSortAsc = findViewById(R.id.btnSortAsc)
        btnSortDesc = findViewById(R.id.btnSortDesc)
        lvCars = findViewById(R.id.lvCars)

        showCars(allCars)

        btnSearch.setOnClickListener {
            val keyword = etSearch.text.toString().trim()

            if (keyword.isEmpty()) {
                showCars(allCars)
                Toast.makeText(this, "Menampilkan semua mobil Ford", Toast.LENGTH_SHORT).show()
            } else {
                val result = linearSearch(keyword)
                showCars(result)

                if (result.isEmpty()) {
                    Toast.makeText(this, "Mobil Ford tidak ditemukan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "${result.size} mobil ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnSortAsc.setOnClickListener {
            bubbleSort(ascending = true)
            refreshListView()
            Toast.makeText(this, "Diurutkan dari A sampai Z", Toast.LENGTH_SHORT).show()
        }

        btnSortDesc.setOnClickListener {
            bubbleSort(ascending = false)
            refreshListView()
            Toast.makeText(this, "Diurutkan dari Z sampai A", Toast.LENGTH_SHORT).show()
        }

        lvCars.setOnItemClickListener { _, _, position, _ ->
            val selectedCar = displayedCars[position]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("CAR_NAME", selectedCar.name)
            intent.putExtra("CAR_CATEGORY", selectedCar.category)
            intent.putExtra("CAR_ENGINE", selectedCar.engine)
            intent.putExtra("CAR_POWER", selectedCar.power)
            intent.putExtra("CAR_PRICE", selectedCar.price)
            intent.putExtra("CAR_TAGLINE", selectedCar.tagline)
            intent.putExtra("CAR_TRANSMISSION", selectedCar.transmission)
            intent.putExtra("CAR_FUEL", selectedCar.fuel)
            intent.putExtra("CAR_SEATS", selectedCar.seats)
            startActivity(intent)
        }
    }

    private fun showCars(cars: List<FordCar>) {
        displayedCars.clear()
        displayedCars.addAll(cars)
        refreshListView()
    }

    private fun refreshListView() {
        val adapter = FordCarAdapter(this, displayedCars)
        lvCars.adapter = adapter
    }

    private fun linearSearch(keyword: String): List<FordCar> {
        val result = ArrayList<FordCar>()

        for (car in allCars) {
            val isMatch = car.name.contains(keyword, ignoreCase = true) ||
                    car.category.contains(keyword, ignoreCase = true) ||
                    car.engine.contains(keyword, ignoreCase = true) ||
                    car.power.contains(keyword, ignoreCase = true) ||
                    car.price.contains(keyword, ignoreCase = true) ||
                    car.tagline.contains(keyword, ignoreCase = true) ||
                    car.transmission.contains(keyword, ignoreCase = true) ||
                    car.fuel.contains(keyword, ignoreCase = true) ||
                    car.seats.contains(keyword, ignoreCase = true)

            if (isMatch) {
                result.add(car)
            }
        }

        return result
    }

    private fun bubbleSort(ascending: Boolean) {
        val size = displayedCars.size

        for (i in 0 until size - 1) {
            for (j in 0 until size - i - 1) {
                val currentName = displayedCars[j].name
                val nextName = displayedCars[j + 1].name

                val shouldSwap = if (ascending) {
                    currentName > nextName
                } else {
                    currentName < nextName
                }

                if (shouldSwap) {
                    val temp = displayedCars[j]
                    displayedCars[j] = displayedCars[j + 1]
                    displayedCars[j + 1] = temp
                }
            }
        }
    }
}