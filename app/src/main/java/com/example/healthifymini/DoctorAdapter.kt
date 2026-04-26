package com.example.healthifymini
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(
    private val context: Context,
    private val doctorList: List<Doctor>
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorImage: ImageView = itemView.findViewById(R.id.doctorImage)
        val doctorName: TextView = itemView.findViewById(R.id.doctorName)
        val doctorSpecialty: TextView = itemView.findViewById(R.id.doctorSpecialty)
        val doctorTitle: TextView = itemView.findViewById(R.id.doctorTitle)
        val doctorRating: RatingBar = itemView.findViewById(R.id.doctorRating)
        val doctorStatus: TextView = itemView.findViewById(R.id.doctorStatus)
        val bookButton: Button = itemView.findViewById(R.id.bookButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.doctorImage.setImageResource(doctor.imageResId)
        holder.doctorName.text = doctor.name
        holder.doctorSpecialty.text = doctor.specialty
        holder.doctorTitle.text = doctor.title
        holder.doctorRating.rating = doctor.rating
        holder.doctorStatus.text = doctor.status

        // Status color
        holder.doctorStatus.setTextColor(
            if (doctor.status.lowercase() == "available")
                context.getColor(android.R.color.holo_green_dark)
            else
                context.getColor(android.R.color.holo_red_dark)
        )

        // Book Button Click: Navigate to BookAppointmentActivity
        holder.bookButton.setOnClickListener {
            val intent = Intent(context, BookAppointmentActivity::class.java)
            intent.putExtra("doctorName", doctor.name)
            intent.putExtra("specialty", doctor.specialty)
            intent.putExtra("title", doctor.title)
            intent.putExtra("rating", doctor.rating)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = doctorList.size
}