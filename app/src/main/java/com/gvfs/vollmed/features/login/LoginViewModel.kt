package com.gvfs.vollmed.features.login

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.exceptions.LoginFailException
import com.gvfs.vollmed.features.doctor.model.DoctorModel
import com.gvfs.vollmed.features.login.service.LoginService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: LoginService
) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>get() =
        _loginSuccess

    var dialogErrorMessage = MutableLiveData<String>(null)
    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                service.login(login, password)
                _loginSuccess.value = true
            } catch (e: LoginFailException) {
                dialogErrorMessage.value = "Login ou senha incorreto"
                _loginSuccess.value = false
            } catch (e: HttpResponseErrorException) {
                dialogErrorMessage.value = "Ocorreu um erro inesperado"
                _loginSuccess.value = false
            }
        }
    }
}