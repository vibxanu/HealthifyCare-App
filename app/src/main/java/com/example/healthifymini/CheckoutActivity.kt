package com.example.healthifymini

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CheckoutActivity : AppCompatActivity() {

    private lateinit var recyclerCheckout: RecyclerView
    private lateinit var tvCheckoutTotal: TextView
    private lateinit var btnBookMedicines: Button
    private lateinit var etFullName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPinCode: EditText
    private lateinit var etContactNumber: EditText
    private lateinit var etDeliveryDate: EditText
    private lateinit var radioGroupPayment: RadioGroup
    private lateinit var rbCashOnDelivery: RadioButton
    private lateinit var rbOnlinePayment: RadioButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvSuccessMessage: TextView
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        initViews()
        setupRecyclerView()
        setupDeliveryDatePicker()
        setupPaymentMethodListener()

        btnBookMedicines.setOnClickListener {
            if (validateForm()) {
                processOrder()
            }
        }
    }

    private fun initViews() {
        recyclerCheckout = findViewById(R.id.recyclerCheckout)
        tvCheckoutTotal = findViewById(R.id.tvCheckoutTotal)
        btnBookMedicines = findViewById(R.id.btnBookMedicines)
        etFullName = findViewById(R.id.etFullName)
        etAddress = findViewById(R.id.etAddress)
        etPinCode = findViewById(R.id.etPinCode)
        etContactNumber = findViewById(R.id.etContactNumber)
        etDeliveryDate = findViewById(R.id.etDeliveryDate)
        radioGroupPayment = findViewById(R.id.radioGroupPayment)
        rbCashOnDelivery = findViewById(R.id.rbCashOnDelivery)
        rbOnlinePayment = findViewById(R.id.rbOnlinePayment)
        progressBar = findViewById(R.id.progressBar)
        tvSuccessMessage = findViewById(R.id.tvSuccessMessage)
    }

    private fun setupRecyclerView() {
        val selectedMedicines = intent.getParcelableArrayListExtra<Medicine>("selectedMedicines") ?: return
        recyclerCheckout.layoutManager = LinearLayoutManager(this)
        recyclerCheckout.adapter = MedicineAdapter(selectedMedicines, showCheckboxes = false)

        val total = selectedMedicines.sumOf { it.price }
        tvCheckoutTotal.text = "Total: Rs. ${String.format("%.2f", total)}"
    }

    private fun setupDeliveryDatePicker() {
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(year, month, day)
            etDeliveryDate.setText(dateFormat.format(calendar.time))
        }

        etDeliveryDate.setOnClickListener {
            DatePickerDialog(
                this, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = System.currentTimeMillis()
                show()
            }
        }
    }

    private fun setupPaymentMethodListener() {
        radioGroupPayment.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbOnlinePayment) {
                Toast.makeText(this, "Online Payment selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        return when {
            etFullName.text.isBlank() -> {
                etFullName.error = "Enter name"; false
            }
            etAddress.text.isBlank() -> {
                etAddress.error = "Enter address"; false
            }
            etPinCode.text.length != 6 -> {
                etPinCode.error = "PIN must be 6 digits"; false
            }
            etContactNumber.text.length != 10 -> {
                etContactNumber.error = "Contact must be 10 digits"; false
            }
            etDeliveryDate.text.isBlank() -> {
                etDeliveryDate.error = "Select delivery date"; false
            }
            radioGroupPayment.checkedRadioButtonId == -1 -> {
                Toast.makeText(this, "Select payment method", Toast.LENGTH_SHORT).show(); false
            }
            else -> true
        }
    }

    private fun processOrder() {
        progressBar.isVisible = true
        btnBookMedicines.isEnabled = false

        Handler().postDelayed({
            progressBar.isVisible = false
            showOrderSuccess()
            CartManager.clearCart()

            Handler().postDelayed({
                showOrderConfirmationPopup()
            }, 1500)
        }, 1500)
    }

    private fun showOrderSuccess() {
        val medicines = intent.getParcelableArrayListExtra<Medicine>("selectedMedicines") ?: listOf()
        val details = medicines.joinToString("\n") { "- ${it.name}: Rs. ${it.price}" }

        val orderSummary = """
            ✅ Order Confirmed!

            🗓️ Delivery: ${etDeliveryDate.text}
            💳 Payment: ${if (rbCashOnDelivery.isChecked) "Cash on Delivery" else "Online"}

            $details
            💰 Total: Rs. ${String.format("%.2f", medicines.sumOf { it.price })}
        """.trimIndent()

        getSharedPreferences("OrderPrefs", MODE_PRIVATE)
            .edit().putString("orderSummary", orderSummary).apply()
    }

    private fun showOrderConfirmationPopup() {
        AlertDialog.Builder(this)
            .setTitle("Order Confirmed")
            .setMessage("Your order has been placed successfully!")
            .setPositiveButton("View Details") { _, _ ->
                val intent = Intent(this, OrderDetailsActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
