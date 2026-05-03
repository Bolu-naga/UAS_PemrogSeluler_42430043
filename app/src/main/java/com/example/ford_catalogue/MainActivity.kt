package com.example.ford_catalogue

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.animation.AnimationUtils
import android.util.Log


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FORD_UAS_PEMROGSELULER"
    }

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnSortAsc: Button
    private lateinit var btnSortDesc: Button
    private lateinit var lvCars: ListView
    private lateinit var tvEmptyState: TextView

    private lateinit var chipAll: TextView
    private lateinit var chipLegend: TextView
    private lateinit var chipSupercar: TextView
    private lateinit var chipSport: TextView
    private lateinit var chipSUV: TextView
    private lateinit var chipTruck: TextView
    private lateinit var chipElectric: TextView
    private lateinit var chipHybrid: TextView
    private lateinit var chipSedan: TextView
    private lateinit var chipVan: TextView
    private lateinit var chipClassic: TextView

    private val allCars = FordCarRepository.getCars()
    private val displayedCars = ArrayList<FordCar>()

    private var selectedCategory = "All"
    private var currentKeyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main)
            Log.i(TAG, "NIM: 42430043 - Aplikasi Ford Garage berhasil dibuka")
            Log.d(TAG, "Nama: Paul - Katalog mobil Ford aktif")

            Log.i(TAG, "MainActivity berhasil dibuka")

            initViews()
            setupCategoryChips()
            showCars(allCars)

            btnSearch.setOnClickListener {
                try {
                    currentKeyword = etSearch.text.toString().trim()
                    Log.d(TAG, "Search keyword: $currentKeyword")
                    applyFilters(showToast = true)
                } catch (e: Exception) {
                    Log.e(TAG, "Error saat search mobil", e)
                    Toast.makeText(this, "Terjadi kesalahan saat mencari mobil", Toast.LENGTH_SHORT).show()
                }
            }

            btnSortAsc.setOnClickListener {
                try {
                    bubbleSort(ascending = true)
                    refreshListView()
                    Log.d(TAG, "Data diurutkan A-Z")
                    Toast.makeText(this, "Garage diurutkan dari A sampai Z", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e(TAG, "Error saat sort A-Z", e)
                    Toast.makeText(this, "Gagal mengurutkan data", Toast.LENGTH_SHORT).show()
                }
            }

            btnSortDesc.setOnClickListener {
                try {
                    bubbleSort(ascending = false)
                    refreshListView()
                    Log.d(TAG, "Data diurutkan Z-A")
                    Toast.makeText(this, "Garage diurutkan dari Z sampai A", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e(TAG, "Error saat sort Z-A", e)
                    Toast.makeText(this, "Gagal mengurutkan data", Toast.LENGTH_SHORT).show()
                }
            }

            lvCars.setOnItemClickListener { _, _, position, _ ->
                try {
                    if (position < 0 || position >= displayedCars.size) {
                        Log.e(TAG, "Invalid car position: $position")
                        Toast.makeText(this, "Data mobil tidak valid", Toast.LENGTH_SHORT).show()
                        return@setOnItemClickListener
                    }

                    val selectedCar = displayedCars[position]
                    Log.i(TAG, "Mobil dipilih: ${selectedCar.name}")

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
                    intent.putExtra("CAR_IMAGE_RES", selectedCar.imageRes)

                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } catch (e: Exception) {
                    Log.e(TAG, "Error saat membuka detail mobil", e)
                    Toast.makeText(this, "Gagal membuka detail mobil", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error fatal di MainActivity", e)
            Toast.makeText(this, "Terjadi kesalahan pada halaman utama", Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnSortAsc = findViewById(R.id.btnSortAsc)
        btnSortDesc = findViewById(R.id.btnSortDesc)
        lvCars = findViewById(R.id.lvCars)
        tvEmptyState = findViewById(R.id.tvEmptyState)

        chipAll = findViewById(R.id.chipAll)
        chipLegend = findViewById(R.id.chipLegend)
        chipSupercar = findViewById(R.id.chipSupercar)
        chipSport = findViewById(R.id.chipSport)
        chipSUV = findViewById(R.id.chipSUV)
        chipTruck = findViewById(R.id.chipTruck)
        chipElectric = findViewById(R.id.chipElectric)
        chipHybrid = findViewById(R.id.chipHybrid)
        chipSedan = findViewById(R.id.chipSedan)
        chipVan = findViewById(R.id.chipVan)
        chipClassic = findViewById(R.id.chipClassic)
    }

    private fun setupCategoryChips() {
        val chips = mapOf(
            chipAll to "All",
            chipLegend to "Legend",
            chipSupercar to "Supercar",
            chipSport to "Sport",
            chipSUV to "SUV",
            chipTruck to "Truck",
            chipElectric to "Electric",
            chipHybrid to "Hybrid",
            chipSedan to "Sedan",
            chipVan to "Van",
            chipClassic to "Classic"
        )

        for ((chip, category) in chips) {
            chip.setOnClickListener {
                selectedCategory = category
                updateChipStyle()
                applyFilters(showToast = true)
            }
        }

        updateChipStyle()
    }

    private fun updateChipStyle() {
        val chips = mapOf(
            chipAll to "All",
            chipLegend to "Legend",
            chipSupercar to "Supercar",
            chipSport to "Sport",
            chipSUV to "SUV",
            chipTruck to "Truck",
            chipElectric to "Electric",
            chipHybrid to "Hybrid",
            chipSedan to "Sedan",
            chipVan to "Van",
            chipClassic to "Classic"
        )

        for ((chip, category) in chips) {
            if (category == selectedCategory) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(ContextCompat.getColor(this, R.color.ford_bg_dark))
                chip.setTypeface(null, android.graphics.Typeface.BOLD)
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(ContextCompat.getColor(this, R.color.ford_text_main))
                chip.setTypeface(null, android.graphics.Typeface.NORMAL)
            }
        }
    }

    private fun applyFilters(showToast: Boolean) {
        try {
            val filteredCars = ArrayList<FordCar>()

            for (car in allCars) {
                val matchCategory = selectedCategory == "All" ||
                        car.category.equals(selectedCategory, ignoreCase = true)

                val matchKeyword = currentKeyword.isEmpty() ||
                        car.name.contains(currentKeyword, ignoreCase = true) ||
                        car.category.contains(currentKeyword, ignoreCase = true) ||
                        car.engine.contains(currentKeyword, ignoreCase = true) ||
                        car.power.contains(currentKeyword, ignoreCase = true) ||
                        car.price.contains(currentKeyword, ignoreCase = true) ||
                        car.tagline.contains(currentKeyword, ignoreCase = true) ||
                        car.transmission.contains(currentKeyword, ignoreCase = true) ||
                        car.fuel.contains(currentKeyword, ignoreCase = true) ||
                        car.seats.contains(currentKeyword, ignoreCase = true)

                if (matchCategory && matchKeyword) {
                    filteredCars.add(car)
                }
            }

            Log.d(TAG, "Filter kategori: $selectedCategory, keyword: $currentKeyword, hasil: ${filteredCars.size}")

            showCars(filteredCars)

            if (showToast) {
                val message = if (filteredCars.isEmpty()) {
                    "Tidak ada mobil yang cocok"
                } else if (selectedCategory == "All" && currentKeyword.isEmpty()) {
                    "Menampilkan semua koleksi Ford"
                } else {
                    "${filteredCars.size} mobil ditemukan"
                }

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saat filter data mobil", e)
            Toast.makeText(this, "Gagal memfilter data mobil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCars(cars: List<FordCar>) {
        displayedCars.clear()
        displayedCars.addAll(cars)
        refreshListView()
        updateEmptyState()
    }

    private fun refreshListView() {
        try {
            val adapter = FordCarAdapter(this, displayedCars)
            lvCars.adapter = adapter

            Log.d(TAG, "ListView diperbarui dengan ${displayedCars.size} mobil")
        } catch (e: Exception) {
            Log.e(TAG, "Error saat refresh ListView", e)
            Toast.makeText(this, "Gagal menampilkan daftar mobil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEmptyState() {
        if (displayedCars.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            lvCars.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            lvCars.visibility = View.VISIBLE
        }
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