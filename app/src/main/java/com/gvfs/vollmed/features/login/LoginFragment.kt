package com.gvfs.vollmed.features.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gvfs.vollmed.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var bind: FragmentLoginBinding? = null
    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment.viewLifecycleOwner
        }
        return bind?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer { success ->
            loginAction(success)
        })

        bind?.formLoginBtnSubmit?.setOnClickListener {
            login()
        }

        bind?.formLoginBtnRoute?.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }

    private fun login() {
        val email = bind?.formLoginEmail?.text.toString()
        val password = bind?.formLoginPassword?.text.toString()
        if (email == "" || password == "") {
            openDialog("Alerta", "Todos os campos precisam ser preenchidos")
        } else {
            lifecycleScope.launch {
                viewModel.login(email, password)
            }
        }

    }

    private fun loginAction(success: Boolean) {
        if(success) {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        } else {
            openDialog("Login falhou", viewModel.dialogErrorMessage.value.toString())
        }

    }

    private fun openDialog(title: String, message: String): Unit {
        return AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK") { dialog_, _ -> dialog_.dismiss() }
                .create()
                .show()
    }

}