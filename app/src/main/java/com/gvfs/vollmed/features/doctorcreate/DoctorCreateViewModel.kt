package com.gvfs.vollmed.features.doctorcreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvfs.vollmed.features.shareddomain.Endereco

class DoctorCreateViewModel : ViewModel() {
    private val _address = MutableLiveData<Endereco>()
    val address: LiveData<Endereco> get() = _address
}