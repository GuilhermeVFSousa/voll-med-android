package com.gvfs.vollmed.features.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

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
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

}