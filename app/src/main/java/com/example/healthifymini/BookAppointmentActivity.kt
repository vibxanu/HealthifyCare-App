package com.example.healthifymini
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var doctorName: TextView
    private lateinit var doctorSpecialty: TextView
    private lateinit var doctorTitle: TextView
    private lateinit var doctorRating: TextView
    private lateinit var patientName: EditText
    private lateinit var patientContact: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var problemDescription: EditText
    private lateinit var appointmentTypeSpinner: Spinner
    private lateinit var selectDateBtn: Button
    private lateinit var selectTimeBtn: Button
    private lateinit var confirmButton: Button

    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        // Initialize views
        backButton = findViewById(R.id.backButton)
        doctorName = findViewById(R.id.appointmentDoctorName)
        doctorSpecialty = findViewById(R.id.appointmentSpecialty)
        doctorTitle = findViewById(R.id.appointmentTitle)
        doctorRating = findViewById(R.id.appointmentRating)
        patientName = findViewById(R.id.patientName)
        patientContact = findViewById(R.id.patientContact)
        genderGroup = findViewById(R.id.genderGroup)
        problemDescription = findViewById(R.id.problemDescription)
        appointmentTypeSpinner = findViewById(R.id.appointmentTypeSpinner)
        selectDateBtn = findViewById(R.id.selectDateBtn)
        selectTimeBtn = findViewById(R.id.selectTimeBtn)
        confirmButton = findViewById(R.id.confirmButton)

        // Receive doctor details from the Intent
        val doctorNameValue = intent.getStringExtra("doctorName")
        val specialty = intent.getStringExtra("specialty")
        val title = intent.getStringExtra("title")
        val rating = intent.getFloatExtra("rating", 0f)

        // Populate UI with the doctor details
        doctorName.text = "Doctor: $doctorNameValue"
        doctorSpecialty.text = specialty
        doctorTitle.text = title
        doctorRating.text = "Rating: $rating"

        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Date Picker
        selectDateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    selectDateBtn.text = "Selected Date: $selectedDate"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // Time Picker
        selectTimeBtn.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    selectedTime = "$hourOfDay:$minute"
                    selectTimeBtn.text = "Selected Time: $selectedTime"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }

        // Confirm Appointment
        confirmButton.setOnClickListener {
            val name = patientName.text.toString()
            val contact = patientContact.text.toString()
            val genderId = genderGroup.checkedRadioButtonId
            val gender = findViewById<RadioButton>(genderId)?.text.toString()
            val problem = problemDescription.text.toString()

            if (name.isEmpty() || contact.isEmpty() || selectedDate == null || selectedTime == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send all appointment details to AppointmentDetailsActivity
            val intent = Intent(this, AppointmentDetailsActivity::class.java)
            intent.putExtra("doctor", doctorNameValue)
            intent.putExtra("name", name)
            intent.putExtra("contact", contact)
            intent.putExtra("gender", gender)
            intent.putExtra("problem", problem)
            intent.putExtra("date", selectedDate)
            intent.putExtra("time", selectedTime)
            startActivity(intent)

            finish()
        }
    }
}