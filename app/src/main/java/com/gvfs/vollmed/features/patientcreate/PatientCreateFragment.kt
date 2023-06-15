package com.gvfs.vollmed.features.patientcreate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentPatientCreateBinding
import com.gvfs.vollmed.extension.addCepMask
import com.gvfs.vollmed.extension.addCpfMask
import com.gvfs.vollmed.extension.addPhoneMask

class PatientCreateFragment : Fragment() {

    companion object {
        fun newInstance() = PatientCreateFragment()
    }

    private val viewModel: PatientCreateViewModel by viewModels()
    private var bind: FragmentPatientCreateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentPatientCreateBinding.inflate(inflater, container, false).apply {
            viewModel = this@PatientCreateFragment.viewModel
            lifecycleOwner = this@PatientCreateFragment.viewLifecycleOwner
        }
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind?.txtPatientCpf?.addCpfMask()
        bind?.txtPatientPhone?.addPhoneMask()
        bind?.txtPatientPostCode?.addCepMask()
    }

}