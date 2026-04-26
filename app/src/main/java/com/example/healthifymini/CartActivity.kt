package com.example.healthifymini

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerCart: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnBack: Button
    private lateinit var btnCheckout: Button
    private lateinit var adapter: MedicineAdapter
    private lateinit var tvEmptyCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerCart = findViewById(R.id.recyclerCart)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnBack = findViewById(R.id.btnBack)
        btnCheckout = findViewById(R.id.btnCheckout)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)

        recyclerCart.layoutManager = LinearLayoutManager(this)
        adapter = MedicineAdapter(
            originalList = CartManager.selectedMedicines,
            showCheckboxes = false,
            onItemSelectedListener = null,
            onItemRemoveListener = object : MedicineAdapter.OnItemRemoveListener {
                override fun onItemRemove(position: Int) {
                    CartManager.selectedMedicines.removeAt(position)
                    updateUI()
                }
            }
        )
        recyclerCart.adapter = adapter

        btnBack.setOnClickListener { finish() }

        btnCheckout.setOnClickListener {
            if (CartManager.selectedMedicines.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putParcelableArrayListExtra("selectedMedicines", ArrayList(CartManager.selectedMedicines))
                startActivity(intent)
            }
        }

        updateUI()
    }

    private fun updateUI() {
        val total = CartManager.getTotalPrice()
        tvTotalPrice.text = "Total: Rs. ${String.format("%.2f", total)}"
        val isEmpty = CartManager.selectedMedicines.isEmpty()
        tvEmptyCart.visibility = if (isEmpty) TextView.VISIBLE else TextView.GONE
        recyclerCart.visibility = if (isEmpty) RecyclerView.GONE else RecyclerView.VISIBLE
        btnCheckout.isEnabled = !isEmpty
    }
}
