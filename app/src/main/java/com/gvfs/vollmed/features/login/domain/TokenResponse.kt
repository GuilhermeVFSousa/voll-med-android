package com.gvfs.vollmed.features.login.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TokenResponse (
    @SerializedName("token")
    val token: String,
    @SerializedName("expiration")
    val expiresIn: String
        )