package com.gvfs.vollmed.utils.serializer

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalSerializationApi::class)
@Serializer(LocalDateTime::class)
object LocalDateTimeSerializer: KSerializer<LocalDateTime> {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val formattedString = formatter.format(value)
        encoder.encodeString(formattedString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val formattedString = decoder.decodeString()
        return LocalDateTime.parse(formattedString, formatter)
    }
}