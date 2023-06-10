package com.gvfs.vollmed.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.gvfs.vollmed.R
import com.gvfs.vollmed.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var bind: FragmentLoginBinding? = null

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var progressIndicator: CircularProgressIndicator? = null
    private var submitText: Button? = null


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

        this@LoginFragment.progressIndicator = bind?.progressIndicator
        this@LoginFragment.submitText = bind?.formLoginBtnSubmit

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
            showSnackBar("Todos os campos precisam ser preenchidos")
        } else {
            lifecycleScope.launch {
                launch {
                    submitText?.text = ""
                    progressIndicator?.visibility = View.VISIBLE
                }
                launch {
                    delay(500)
                    viewModel.login(email, password)
                }
            }
        }
    }

    private fun loginAction(success: Boolean) {
        if(success) {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        } else {
            showSnackBar(viewModel.dialogErrorMessage.value.toString())
        }
        progressIndicator?.visibility = View.INVISIBLE
        submitText?.text = context?.getString(R.string.login_enter)
    }

    private fun showSnackBar(message: String): Unit {
        return Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

}