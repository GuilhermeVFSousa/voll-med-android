package com.gvfs.vollmed.features.doctorcreate

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gvfs.vollmed.databinding.FragmentDoctorCreateBinding
import com.gvfs.vollmed.extension.addCepMask
import com.gvfs.vollmed.extension.addPhoneMask
import com.gvfs.vollmed.features.alert.AlertEvent
import com.gvfs.vollmed.features.doctor.domain.DoctorCreate
import com.gvfs.vollmed.features.shareddomain.Endereco
import com.gvfs.vollmed.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class DoctorCreateFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorCreateFragment()
    }

    private val viewModel: DoctorCreateViewModel by viewModels()
    private var bind: FragmentDoctorCreateBinding? = null
    private var specialties: MutableList<String> = mutableListOf();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentDoctorCreateBinding.inflate(inflater, container, false).apply {
            viewModel = this@DoctorCreateFragment.viewModel
            lifecycleOwner = this@DoctorCreateFragment.viewLifecycleOwner
        }
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fieldName = bind?.txtDoctorName
        val fieldEmail = bind?.txtDoctorEmail
        val fieldPostCode = bind?.txtDoctorPostCode
        val fieldCrm = bind?.txtDoctorCrm
        val fieldPhone = bind?.txtDoctorPhone
        val fieldStreet = bind?.txtDoctorStreet
        val fieldDistrict = bind?.txtDoctorDistrict
        val fieldNumber = bind?.txtDoctorHouseNumber
        val fieldComplement = bind?.txtDoctorComplement
        val fieldCity = bind?.txtDoctorCity
        val fieldUf = bind?.txtDoctorUf
        var fieldSpecialty: String? = null

        fieldPhone?.addPhoneMask()
        fieldPostCode?.addCepMask()

        viewModel.events.observe( viewLifecycleOwner) { event ->
            when(event) {
                is AlertEvent.CepNotFound -> fieldPostCode?.error = event.message
                else -> {}
            }
        }

        viewModel.createEvent.observe(viewLifecycleOwner) { event ->
            when(event) {
                is AlertEvent.DoctorCreated -> {
                    findNavController().popBackStack()
                    showSnackBar(event.message)
                }
                is AlertEvent.DoctorCreatedError -> {
                    showSnackBar(event.message)
                    bind?.btnSubmit?.isEnabled = true
                }
                else -> {}
            }
        }

        bind?.btnCep?.setOnClickListener {
            if (!Utils.validateField(fieldPostCode, 8, "O CEP precisa ter 8 números")) return@setOnClickListener
            viewModel.getCep(fieldPostCode?.text.toString())
        }

        bind?.btnSubmit?.setOnClickListener {
            if (!Utils.validateField(fieldPostCode, 8, "O CEP precisa ter 8 números") ||
                !Utils.validateField(fieldPhone, 10, "O Telefone precisa ter ao menos 10 números") ||
                !Utils.validateField(fieldName, null, null) ||
                !Utils.validateField(fieldEmail, null, null) ||
                !Utils.validateField(fieldStreet, null, null) ||
                !Utils.validateField(fieldDistrict, null, null) ||
                !Utils.validateField(fieldNumber, null, null) ||
                !Utils.validateField(fieldCity, null, null) ||
                !Utils.validateField(fieldUf, null, null) ||
                !checkSelectedSpecialty(fieldSpecialty)
            ) return@setOnClickListener

            submit(fieldName?.text.toString(),
                fieldEmail?.text.toString(),
                fieldPostCode?.text.toString(),
                fieldCrm?.text.toString(),
                fieldPhone?.text.toString(),
                fieldStreet?.text.toString(),
                fieldDistrict?.text.toString(),
                fieldNumber?.text.toString(),
                fieldComplement?.text.toString(),
                fieldCity?.text.toString(),
                fieldUf?.text.toString(),
                fieldSpecialty!!
            )

        }

        val spinner = bind?.spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specialties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        viewModel.getSpecialties()
        viewModel.specialties.observe(viewLifecycleOwner) { specialties_ ->
            specialties.clear()
            specialties.addAll(specialties_)
            adapter.notifyDataSetChanged()
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fieldSpecialty = if (position == 0) {
                    null
                } else {
                    specialties[position]
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submit(
        fieldName : String,
        fieldEmail : String,
        fieldPostCode : String,
        fieldCrm : String,
        fieldPhone : String,
        fieldStreet : String,
        fieldDistrict : String,
        fieldNumber : String,
        fieldComplement : String,
        fieldCity : String,
        fieldUf : String,
        fieldSpecialty : String
    ) {
        val address = Endereco(fieldStreet, fieldDistrict, fieldPostCode, fieldNumber, fieldComplement,fieldCity, fieldUf)
        address.cep = address.cep.replace("-", "")
        val doctor = DoctorCreate(fieldName, fieldEmail, fieldPhone, fieldCrm, fieldSpecialty, address)

        viewModel.create(doctor)
        bind?.btnSubmit?.isEnabled = false
    }

    private fun showSnackBar(message: String): Unit {
        return Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
    private fun checkSelectedSpecialty(fieldSpecialty: String?): Boolean {
        return if (fieldSpecialty == null){
            showSnackBar("Selecione uma especialidade")
            false
        } else true
    }

}