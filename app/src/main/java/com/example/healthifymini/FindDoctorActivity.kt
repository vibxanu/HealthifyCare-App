package com.example.healthifymini

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FindDoctorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorList: MutableList<Doctor>
    private lateinit var fullDoctorList: MutableList<Doctor>
    private lateinit var adapter: DoctorAdapter
    private lateinit var backButton: Button
    private lateinit var searchBar: EditText  // Search bar reference
    private lateinit var availableCheckbox: CheckBox  // "Available" checkbox
    private lateinit var offlineCheckbox: CheckBox    // "Offline" checkbox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctor)

        // Initialize views
        searchBar = findViewById(R.id.searchBar)
        recyclerView = findViewById(R.id.doctorRecyclerView)
        availableCheckbox = findViewById(R.id.availableCheckbox)
        offlineCheckbox = findViewById(R.id.offlineCheckbox)
        backButton = findViewById(R.id.backButton)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Load data
        loadDoctorData()
        doctorList = fullDoctorList.toMutableList() // Create a copy for displaying
        adapter = DoctorAdapter(this, doctorList)
        recyclerView.adapter = adapter

        // Back Button click listener
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Search bar text watcher
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterDoctors(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Filter checkboxes listener
        availableCheckbox.setOnCheckedChangeListener { _, _ ->
            filterDoctors(searchBar.text.toString())
        }

        offlineCheckbox.setOnCheckedChangeListener { _, _ ->
            filterDoctors(searchBar.text.toString())
        }
    }

    // Filter doctors based on search query and checkboxes
    private fun filterDoctors(query: String) {
        val showAvailable = availableCheckbox.isChecked
        val showOffline = offlineCheckbox.isChecked

        val filteredList = fullDoctorList.filter {
            val matchesSearch = it.name.contains(query, ignoreCase = true) || it.specialty.contains(query, ignoreCase = true)

            val matchesStatus = when {
                showAvailable && showOffline -> true
                showAvailable -> it.status.equals("Available", ignoreCase = true)
                showOffline -> it.status.equals("Offline", ignoreCase = true)
                else -> true
            }

            matchesSearch && matchesStatus
        }

        // Update doctor list
        doctorList.clear()
        doctorList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

    // Load doctor data
    private fun loadDoctorData() {
        fullDoctorList = mutableListOf(
            Doctor(
                name = "Dr. Ayesha Khan",
                specialty = "Cardiologist",
                title = "MBBS, FCPS",
                rating = 4.5f,
                status = "Available",
                imageResId = R.drawable.doctor
            ),
            Doctor(
                name = "Dr. Sara Ali",
                specialty = "Dermatologist",
                title = "MBBS, MCPS",
                rating = 4.0f,
                status = "Offline",
                imageResId = R.drawable.doctor
            ),
            Doctor(
                name = "Dr. Ahmed Raza",
                specialty = "Neurologist",
                title = "MBBS, FCPS",
                rating = 4.8f,
                status = "Available",
                imageResId = R.drawable.doctor
            ),
            Doctor(
                name = "Dr. Hina Sheikh",
                specialty = "Dentist",
                title = "BDS, MDS",
                rating = 3.9f,
                status = "Offline",
                imageResId = R.drawable.doctor
            ),
            Doctor(
                name = "Dr. Bilal Aslam",
                specialty = "Eye Specialist",
                title = "MBBS, DOMS",
                rating = 4.2f,
                status = "Available",
                imageResId = R.drawable.doctor
            )
        )
    }
}