package com.gvfs.vollmed.features.patientcreate

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.gvfs.vollmed.databinding.FragmentPatientCreateBinding
import com.gvfs.vollmed.extension.addCepMask
import com.gvfs.vollmed.extension.addCpfMask
import com.gvfs.vollmed.extension.addPhoneMask
import com.gvfs.vollmed.features.alert.AlertEvent
import com.gvfs.vollmed.features.patient.domain.PatientCreate
import com.gvfs.vollmed.features.shareddomain.Endereco
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fieldName = bind?.txtPatientName
        val fieldEmail = bind?.txtPatientEmail
        val fieldPostCode = bind?.txtPatientPostCode
        val fieldCpf = bind?.txtPatientCpf
        val fieldPhone = bind?.txtPatientPhone
        val fieldStreet = bind?.txtPatientStreet
        val fieldDistrict = bind?.txtPatientDistrict
        val fieldNumber = bind?.txtPatientHouseNumber
        val fieldComplement = bind?.txtPatientComplement
        val fieldCity = bind?.txtPatientCity
        val fieldUf = bind?.txtPatientUf

        fieldCpf?.addCpfMask()
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
                is AlertEvent.PatientCreated -> {
                    findNavController().popBackStack()
                    showSnackBar(event.message)
                }
                is AlertEvent.PatientCreatedError -> {
                    showSnackBar(event.message)
                    bind?.btnSubmit?.isEnabled = true
                }
                else -> {}
            }
        }

        bind?.btnCep?.setOnClickListener {
            if (!validateField(fieldPostCode, 8, "O CEP precisa ter 8 números")) return@setOnClickListener
                viewModel.getCep(fieldPostCode?.text.toString())
        }

        bind?.btnSubmit?.setOnClickListener {
            if (!validateField(fieldPostCode, 8, "O CEP precisa ter 8 números") ||
                !validateField(fieldCpf, 11, "O CPF precisa ter 11 números") ||
                !validateField(fieldPhone, 10, "O Telefone precisa ter ao menos 10 números") ||
                !validateField(fieldName, null, null) ||
                !validateField(fieldEmail, null, null) ||
                !validateField(fieldStreet, null, null) ||
                !validateField(fieldDistrict, null, null) ||
                !validateField(fieldNumber, null, null) ||
                !validateField(fieldCity, null, null) ||
                !validateField(fieldUf, null, null)) return@setOnClickListener

            submit(fieldName?.text.toString(),
                    fieldEmail?.text.toString(),
                    fieldPostCode?.text.toString(),
                    fieldCpf?.text.toString(),
                    fieldPhone?.text.toString(),
                    fieldStreet?.text.toString(),
                    fieldDistrict?.text.toString(),
                    fieldNumber?.text.toString(),
                    fieldComplement?.text.toString(),
                    fieldCity?.text.toString(),
                    fieldUf?.text.toString()
            )
        }
    }

    private fun showSnackBar(message: String): Unit {
        return Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun validateField(
        field: TextInputEditText?,
        length: Int?,
        message: String?): Boolean {
        var fieldText = field?.text.toString()
        fieldText = fieldText.replace("-", "")
        fieldText = fieldText.replace(".", "")
        fieldText = fieldText.replace("(", "")
        fieldText = fieldText.replace(")", "")
        if ( fieldText.isEmpty()){
            field?.error = "O campo é obrigatório"
            return false
        }
        if (length != null){
            if (fieldText.length < length){
                field?.error = message
                return false
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submit(
        fieldName : String,
        fieldEmail : String,
        fieldPostCode : String,
        fieldCpf : String,
        fieldPhone : String,
        fieldStreet : String,
        fieldDistrict : String,
        fieldNumber : String,
        fieldComplement : String,
        fieldCity : String,
        fieldUf : String
    ) {
        val address = Endereco(fieldStreet, fieldDistrict, fieldPostCode, fieldNumber, fieldComplement,fieldCity, fieldUf)
        address.cep = address.cep.replace("-", "")
        val patient = PatientCreate(fieldName, fieldEmail, fieldPhone, fieldCpf, address)

        viewModel.create(patient)
        bind?.btnSubmit?.isEnabled = false

    }

}