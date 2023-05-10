package com.gvfs.vollmed.features.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvfs.vollmed.databinding.FragmentDoctorListItemBinding
import com.gvfs.vollmed.features.doctor.model.DoctorModel

class DoctorsAdapter(private val doctors: List<DoctorModel>): RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    inner class ViewHolder(val binder: FragmentDoctorListItemBinding): RecyclerView.ViewHolder(binder.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = FragmentDoctorListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor = doctors[position]
        holder.binder.apply {
            doctor = currentDoctor
            executePendingBindings()
        }
    }
}