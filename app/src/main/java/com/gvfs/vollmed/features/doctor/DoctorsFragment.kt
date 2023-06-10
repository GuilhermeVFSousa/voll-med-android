package com.gvfs.vollmed.features.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gvfs.vollmed.databinding.FragmentDoctorsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorsFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorsFragment()
    }

    private val viewModel: DoctorsViewModel by viewModels()
    private var bind: FragmentDoctorsBinding? = null
    private var adapter: DoctorsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       bind = FragmentDoctorsBinding.inflate(inflater, container, false).apply {
           viewModel = this@DoctorsFragment.viewModel
           adapter = this@DoctorsFragment.adapter
           lifecycleOwner = this@DoctorsFragment.viewLifecycleOwner
       }
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind?.topAppBar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getDoctors()
        viewModel.doctor.observe(viewLifecycleOwner) { doctors ->
            adapter = DoctorsAdapter(doctors)
            bind?.progressIndicator?.visibility = View.GONE
            bind?.rvDoctors?.adapter = adapter
        }
    }

}