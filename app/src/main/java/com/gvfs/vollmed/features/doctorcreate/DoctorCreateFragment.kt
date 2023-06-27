package com.gvfs.vollmed.features.doctorcreate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentDoctorCreateBinding

class DoctorCreateFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorCreateFragment()
    }

    private val viewModel: DoctorCreateViewModel by viewModels()
    private var bind: FragmentDoctorCreateBinding? = null

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

        val spinner = bind?.spinner
        val listaStrings = listOf("Selecione uma opção", "Item 1", "Item 2", "Item 3", "Item 4")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaStrings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    // Lógica para tratar a seleção do item inicial
                } else {
                    val selectedItem = listaStrings[position]
                    // Lógica para tratar a seleção dos outros itens
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Lógica para tratar quando nenhum item é selecionado
            }
        }

    }


}