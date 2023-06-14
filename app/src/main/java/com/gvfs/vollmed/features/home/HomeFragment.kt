package com.gvfs.vollmed.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentHomeBinding
import com.gvfs.vollmed.extension.getBrazilianFormatString
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment(), OnSelectDateListener, OnDayClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private var bind: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       bind =  FragmentHomeBinding.inflate(inflater, container, false).apply {
       }
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind?.llMedicos?.setOnClickListener {
            view.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDoctorsFragment()
            )
        }

        bind?.llPacientes?.setOnClickListener {
            view.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPatientsFragment()
            )
        }

        bind?.llConsultas?.setOnClickListener {
            view.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAppointmentFragment()
            )
        }

//        bind?.llConsultas?.setOnClickListener {
//            openDatePicker()
//        }
//        bind?.calendarView?.setOnDayClickListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

    private fun openDatePicker() {
        DatePickerBuilder(this.requireContext(), this)
            .pickerType(CalendarView.ONE_DAY_PICKER)
            .headerColor(R.color.colorPrimary)
            .todayLabelColor(R.color.colorSecondary)
            .selectionColor(R.color.colorSecondaryVariant)
            .dialogButtonsColor(R.color.colorSecondary)
            .build()
            .show()
    }

    override fun onSelect(calendar: List<Calendar>) {
        val stringDate = SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).format(calendar[0].time)
        println("::::::::::::::::::::::${stringDate}")
    }

    override fun onDayClick(eventDay: EventDay) {
        println("::::::::::::::::::::::${eventDay.calendar.time}")
    }
    fun getFormattedDate(date: LocalDateTime): String {
        return SimpleDateFormat("HH:mm dd/MM/yyyy",
            Locale.getDefault()).format(date)
    }


}