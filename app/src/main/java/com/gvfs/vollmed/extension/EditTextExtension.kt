package com.gvfs.vollmed.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addCpfMask() {
    val maskWatcher = object : TextWatcher {
        private var isUpdating = false
        private var currentText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) {
                isUpdating = false
                return
            }

            var text = s.toString().replace("[^\\d]".toRegex(), "")
            if (text.length > 11) {
                text = text.substring(0, 11)// text size
            }

            val formattedText = StringBuilder()
            for (i in text.indices) {
                val currentChar = text[i]
                if (i == 3 || i == 6) {
                    formattedText.append('.')
                } else if (i == 9) {
                    formattedText.append('-')
                }
                formattedText.append(currentChar)
            }

            isUpdating = true
            currentText = formattedText.toString()
            setText(currentText)
            setSelection(currentText.length)
        }
    }

    addTextChangedListener(maskWatcher)
}

fun EditText.addPhoneMask() {
    val maskWatcher = object : TextWatcher {
        private var isUpdating = false
        private var currentText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) {
                isUpdating = false
                return
            }

            var text = s.toString().replace("[^\\d]".toRegex(), "")
            if (text.length > 11) {
                text = text.substring(0, 11)
            }

            val formattedText = java.lang.StringBuilder()
            for (i in text.indices) {
                val currentChar = text[i]
                if (i == 0) {
                    formattedText.append('(')
                } else if (i == 2) {
                    formattedText.append(')')
                } else if (i == 6) {
                    formattedText.append('-')
                } else if (i == 10) {
                    formattedText.deleteAt(8)
                    formattedText.insert(9, '-')
                }
                formattedText.append(currentChar)
            }

            isUpdating = true
            currentText = formattedText.toString()
            setText(currentText)
            setSelection(currentText.length)
        }
    }

    addTextChangedListener(maskWatcher)
}

fun EditText.addCepMask() {
    val maskWatcher = object : TextWatcher {
        private var isUpdating = false
        private var currentText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) {
                isUpdating = false
                return
            }

            var text = s.toString().replace("[^\\d]".toRegex(), "")
            if (text.length > 8) {
                text = text.substring(0, 8)// text size
            }

            val formattedText = StringBuilder()
            for (i in text.indices) {
                val currentChar = text[i]
                if (i == 5) {
                    formattedText.append('-')
                }
                formattedText.append(currentChar)
            }

            isUpdating = true
            currentText = formattedText.toString()
            setText(currentText)
            setSelection(currentText.length)
        }
    }

    addTextChangedListener(maskWatcher)
}
