package com.gvfs.vollmed.utils

import com.google.android.material.textfield.TextInputEditText

class Utils {
    companion object {
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