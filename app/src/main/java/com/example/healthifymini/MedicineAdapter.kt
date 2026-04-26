package com.example.healthifymini

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(
    private val originalList: List<Medicine>,
    private val showCheckboxes: Boolean,
    private val onItemSelectedListener: ((Medicine, Int) -> Unit)? = null,
    private val onItemRemoveListener: OnItemRemoveListener? = null
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    interface OnItemRemoveListener {
        fun onItemRemove(position: Int)
    }

    private var filteredList: List<Medicine> = originalList

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMedicine: ImageView = itemView.findViewById(R.id.ivMedicine)
        val tvName: TextView = itemView.findViewById(R.id.tvMedicineName)
        val tvDetails: TextView = itemView.findViewById(R.id.tvMedicineDetails)
        val tvPrice: TextView = itemView.findViewById(R.id.tvMedicinePrice)
        val checkBox: CheckBox = itemView.findViewById(R.id.cbSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = filteredList[position]

        holder.ivMedicine.setImageResource(medicine.imageResId)
        holder.tvName.text = medicine.name
        holder.tvDetails.text = medicine.details
        holder.tvPrice.text = "Rs. ${"%.2f".format(medicine.price)}"

        holder.checkBox.visibility = if (showCheckboxes) View.VISIBLE else View.GONE
        holder.checkBox.isChecked = medicine.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            medicine.isSelected = isChecked
            onItemSelectedListener?.invoke(medicine, position)
        }

        holder.itemView.setOnClickListener {
            if (!showCheckboxes) {
                onItemRemoveListener?.onItemRemove(position)
            } else {
                medicine.isSelected = !medicine.isSelected
                holder.checkBox.isChecked = medicine.isSelected
                onItemSelectedListener?.invoke(medicine, position)
            }
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filterMedicines(query: String) {
        filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.details.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}