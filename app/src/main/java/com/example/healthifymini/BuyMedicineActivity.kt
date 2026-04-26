package com.example.healthifymini

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuyMedicineActivity : AppCompatActivity() {

    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var medicineList: List<Medicine>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medicine)

        val recyclerView = findViewById<RecyclerView>(R.id.rvMedicines)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnGoToCart = findViewById<Button>(R.id.btnGoToCart)

        medicineList = listOf(
            Medicine("Paracetamol", "500mg tablet", R.drawable.ic_medicine_placeholder, 50.0),
            Medicine("Amoxicillin", "250mg capsule", R.drawable.ic_medicine_placeholder1, 80.0),
            Medicine("Ibuprofen", "200mg tablet", R.drawable.ic_medicine_placeholder2, 60.0),
            Medicine("Cough Syrup", "100ml", R.drawable.ic_medicine_placeholder3, 90.0),
            Medicine("Vitamin C", "10 tablets", R.drawable.ic_medicine_placeholder4, 40.0),
            Medicine("Antacid", "5g powder", R.drawable.ic_medicine_placeholder5, 20.0)
        )

        medicineAdapter = MedicineAdapter(
            originalList = medicineList,
            showCheckboxes = true,
            onItemSelectedListener = { medicine, _ ->
                if (medicine.isSelected) {
                    CartManager.addMedicine(medicine)
                } else {
                    CartManager.removeMedicine(medicine)
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = medicineAdapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                medicineAdapter.filterMedicines(s.toString())
            }
        })

        btnGoToCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
