package com.example.healthifymini

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvOrderSummary = findViewById<TextView>(R.id.tvOrderSummary)
        val btnGoToHome = findViewById<Button>(R.id.btnGoToHome)

        val sharedPref = getSharedPreferences("OrderPrefs", MODE_PRIVATE)
        val summary = sharedPref.getString("orderSummary", "No recent orders found.")
        tvOrderSummary.text = summary

        btnGoToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
