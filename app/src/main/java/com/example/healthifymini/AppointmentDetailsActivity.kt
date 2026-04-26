package com.example.healthifymini
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class AppointmentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_details)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val homeButton = findViewById<ImageButton>(R.id.homeButton)

        // Back button functionality - previous screen par wapas jao
        backButton.setOnClickListener {
            finish()  // Activity close karke pichli screen par chala jao
        }

        // Home button functionality - MainActivity par jao
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Agar aap chahte ho ke back press se ye wapas na aaye:
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        // Appointment details ko Intent se le kar TextViews me set karo
        val detailDoctor = findViewById<TextView>(R.id.detailDoctor)
        val detailName = findViewById<TextView>(R.id.detailName)
        val detailContact = findViewById<TextView>(R.id.detailContact)
        val detailGender = findViewById<TextView>(R.id.detailGender)
        val detailProblem = findViewById<TextView>(R.id.detailProblem)
        val detailDate = findViewById<TextView>(R.id.detailDate)
        val detailTime = findViewById<TextView>(R.id.detailTime)

        // Intent se data lena (jo BookAppointmentActivity se bheja gaya hoga)
        val doctor = intent.getStringExtra("doctor") ?: "N/A"
        val name = intent.getStringExtra("name") ?: "N/A"
        val contact = intent.getStringExtra("contact") ?: "N/A"
        val gender = intent.getStringExtra("gender") ?: "N/A"
        val problem = intent.getStringExtra("problem") ?: "N/A"
        val date = intent.getStringExtra("date") ?: "N/A"
        val time = intent.getStringExtra("time") ?: "N/A"

        // TextViews me data set karna
        detailDoctor.text = "👨‍⚕️ Doctor: $doctor"
        detailName.text = "🙍‍♂️ Name: $name"
        detailContact.text = "📱 Contact: $contact"
        detailGender.text = "🚻 Gender: $gender"
        detailProblem.text = "💬 Problem: $problem"
        detailDate.text = "📅 Date: $date"
        detailTime.text = "⏰ Time: $time"
    }
}