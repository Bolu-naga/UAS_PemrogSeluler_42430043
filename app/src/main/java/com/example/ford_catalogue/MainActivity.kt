package com.example.ford_catalogue

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
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

    private val fordCars = arrayOf(
        "Ford Fiesta", "Ford Focus", "Ford S-Max", "Ford Fusion", "Ford Taurus",
        "Ford Everest", "Ford Bronco", "Ford Explorer", "Ford Escape", "Ford Expedition",
        "Ford Mustang GT", "Ford Mustang Mach 1", "Ford Focus RS", "Ford Shelby GT500",
        "Ford Ranger", "Ford F-150", "Ford Maverick", "Ford GT", "Ford GT40"
    )

    private val displayList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnSortAsc = findViewById(R.id.btnSortAsc)
        btnSortDesc = findViewById(R.id.btnSortDesc)
        lvCars = findViewById(R.id.lvCars)

        displayList.addAll(fordCars)

        adapter = ArrayAdapter(this, R.layout.item_car, displayList)
        lvCars.adapter = adapter

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Masukkan nama mobil Ford terlebih dahulu!", Toast.LENGTH_SHORT).show()
                displayList.clear()
                displayList.addAll(fordCars)
                adapter.notifyDataSetChanged()
            } else {
                linearSearch(query)
            }
        }

        btnSortAsc.setOnClickListener { bubbleSort(true) }
        btnSortDesc.setOnClickListener { bubbleSort(false) }

        lvCars.setOnItemClickListener { _, _, position, _ ->
            val selectedCar = displayList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("CAR_NAME", selectedCar)
            startActivity(intent)
        }
    }

    private fun linearSearch(query: String) {
        displayList.clear()

        for (i in fordCars.indices) {
            if (fordCars[i].contains(query, ignoreCase = true)) {
                displayList.add(fordCars[i])
            }
        }

        adapter.notifyDataSetChanged()

        if (displayList.isEmpty()) {
            Toast.makeText(this, "Mobil tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bubbleSort(ascending: Boolean) {
        val n = displayList.size

        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                var shouldSwap = false
                val compareResult = displayList[j].compareTo(displayList[j + 1], ignoreCase = true)

                if (ascending) {
                    if (compareResult > 0) shouldSwap = true
                } else {
                    if (compareResult < 0) shouldSwap = true
                }

                if (shouldSwap) {
                    val temp = displayList[j]
                    displayList[j] = displayList[j + 1]
                    displayList[j + 1] = temp
                }
            }
        }
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Katalog berhasil diurutkan!", Toast.LENGTH_SHORT).show()
    }
}