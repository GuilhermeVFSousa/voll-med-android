package com.gvfs.vollmed.features.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvfs.vollmed.databinding.FragmentPatientListItemBinding
import com.gvfs.vollmed.features.doctor.DoctorsAdapter
import com.gvfs.vollmed.features.patient.domain.PatientResume

class PatientsAdapter(
    private val patients: List<PatientResume>
    ): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {

        inner class ViewHolder(
            val binder: FragmentPatientListItemBinding
        ): RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentPatientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPatient = patients[position]
        holder.binder.apply {
            patient = currentPatient
            executePendingBindings()
        }
    }
}