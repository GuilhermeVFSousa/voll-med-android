package com.gvfs.vollmed.features.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentAppointmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = AppointmentFragment()
    }

    private val viewModel: AppointmentViewModel by viewModels()
    private var bind: FragmentAppointmentBinding? = null
    private var adapter: AppointmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentAppointmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@AppointmentFragment.viewModel
            adapter = this@AppointmentFragment.adapter
            lifecycleOwner = this@AppointmentFragment.viewLifecycleOwner
        }
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAppointments(1, "")
        viewModel.appointments.observe(viewLifecycleOwner){ appointments ->
            adapter = AppointmentAdapter(appointments)
            bind?.rvAppointments?.adapter = adapter

        }
    }


}