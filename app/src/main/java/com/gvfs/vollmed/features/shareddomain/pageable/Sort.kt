package com.gvfs.vollmed.features.shareddomain.pageable

import kotlinx.serialization.Serializable

@Serializable
data class Sort (
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
    )