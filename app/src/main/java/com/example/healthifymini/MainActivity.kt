package com.example.healthifymini
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buyMedicineCard = findViewById<CardView>(R.id.cardBuyMedicine)
        val orderDetailsCard = findViewById<CardView>(R.id.cardOrderDetails)
        val findDoctorCard = findViewById<CardView>(R.id.cardFindDoctor)
        val logoutCard = findViewById<CardView>(R.id.cardLogout)

        // 🎬 Animation load karo (from anim folder)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_fade_up)

        // 💃 Animation apply karo sab cards pe
        buyMedicineCard.startAnimation(animation)
        orderDetailsCard.startAnimation(animation)
        findDoctorCard.startAnimation(animation)
        logoutCard.startAnimation(animation)

        // 🔔 Click listeners
        buyMedicineCard.setOnClickListener {
            val intent = Intent(this, BuyMedicineActivity::class.java)
            startActivity(intent)
        }


        orderDetailsCard.setOnClickListener {
            val intent = Intent(this,OrderDetailsActivity::class.java)
            startActivity(intent)

        }

        findDoctorCard.setOnClickListener {
            val intent = Intent(this, FindDoctorActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // Apply fade-in transition
        }
        logoutCard.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // current activity close
        }
    }
}