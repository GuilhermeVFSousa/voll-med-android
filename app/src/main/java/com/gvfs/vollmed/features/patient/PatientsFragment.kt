package com.gvfs.vollmed.features.patient

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gvfs.vollmed.databinding.FragmentPatientsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientsFragment : Fragment() {

    companion object {
        fun newInstance() = PatientsFragment()
    }

    private val viewModel:PatientsViewModel by viewModels()
    private var bind: FragmentPatientsBinding? = null
    private var adapter: PatientsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentPatientsBinding.inflate(inflater, container, false).apply {
            viewModel = this@PatientsFragment.viewModel
            adapter = this@PatientsFragment.adapter
            lifecycleOwner = this@PatientsFragment.viewLifecycleOwner
        }
        return bind?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind?.topAppBar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        bind?.fltBtn?.setOnClickListener {
            findNavController().navigate(PatientsFragmentDirections.actionPatientsFragmentToPatientCreateFragment(1))
        }

        viewModel.getPatients()

        viewModel.patients.observe(viewLifecycleOwner) { patients ->
            adapter = PatientsAdapter(patients)
            bind?.progressIndicator?.visibility = View.GONE
            bind?.rvPatients?.adapter = adapter
        }

    }

}