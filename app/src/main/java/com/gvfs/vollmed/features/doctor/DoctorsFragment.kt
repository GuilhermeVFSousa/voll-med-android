package com.gvfs.vollmed.features.doctor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentDoctorsBinding

class DoctorsFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorsFragment()
    }

    private val viewModel: DoctorsViewModel by viewModels()
    private lateinit var adapter: DoctorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = DoctorsAdapter(viewModel.getDoctors())

       val bind = FragmentDoctorsBinding.inflate(inflater, container, false).apply {
           viewModel = this@DoctorsFragment.viewModel
           adapter = this@DoctorsFragment.adapter
           lifecycleOwner = this@DoctorsFragment.viewLifecycleOwner
       }
        return bind.root
    }

}