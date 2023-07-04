package com.gvfs.vollmed.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText

class Utils {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun validateField(
            field: TextInputEditText?,
            length: Int?,
            message: String?
        ): Boolean {
            var fieldText = field?.text.toString()
            fieldText = fieldText.replace("-", "")
            fieldText = fieldText.replace(".", "")
            fieldText = fieldText.replace("(", "")
            fieldText = fieldText.replace(")", "")
            if (fieldText.isEmpty()) {
                field?.error = "O campo é obrigatório"
                field?.requestFocus()
                return false
            }
            if (length != null) {
                if (fieldText.length < length) {
                    field?.error = message
                    return false
                }
            }
            return true
        }
    }
}