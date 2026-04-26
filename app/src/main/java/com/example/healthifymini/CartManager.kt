package com.example.healthifymini

object CartManager {
    val selectedMedicines = mutableListOf<Medicine>()

    fun addMedicine(medicine: Medicine) {
        if (!selectedMedicines.contains(medicine)) {
            selectedMedicines.add(medicine)
        }
    }

    fun removeMedicine(medicine: Medicine) {
        selectedMedicines.remove(medicine)
    }

    fun clearCart() {
        selectedMedicines.clear()
    }

    fun getTotalPrice(): Double {
        return selectedMedicines.sumOf { it.price }
    }
}