package com.gvfs.vollmed.features.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvfs.vollmed.databinding.FragmentAppointmentListItemBinding
import com.gvfs.vollmed.features.appointment.domain.Appointment

class AppointmentAdapter(
    private val appointments: List<Appointment>
): RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binder:FragmentAppointmentListItemBinding
    ): RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentAppointmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAppointment = appointments[position]
        holder.binder.apply {
            appointment = currentAppointment
            executePendingBindings()
        }
    }
}