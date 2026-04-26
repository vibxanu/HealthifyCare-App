package com.example.healthifymini


// Define the Doctor data class to hold doctor details
data class Doctor(
    val name: String,             // Doctor's name
    val specialty: String,        // Doctor's specialty (e.g., Cardiologist)
    val title: String,            // Doctor's title (e.g., MBBS, FCPS)
    val rating: Float,            // Doctor's rating (e.g., 4.5f)
    val status: String,           // Doctor's availability status (e.g., Available, Offline)
    val imageResId: Int           // Doctor's image resource ID (e.g., R.drawable.doctor_image)
)