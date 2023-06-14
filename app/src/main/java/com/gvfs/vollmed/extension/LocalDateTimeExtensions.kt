package com.gvfs.vollmed.extension

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

fun LocalDateTime.getBrazilianFormatString(date: LocalDateTime): String {
    return SimpleDateFormat("HH:mm dd/MM/yyyy",
        Locale.getDefault()).format(date)
}