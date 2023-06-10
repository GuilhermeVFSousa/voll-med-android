package com.gvfs.vollmed.features.shareddomain.pageable

import kotlinx.serialization.Serializable

@Serializable
data class Pageable (
    val sort: Sort,
    val offset: Long,
    val pageNumber: Long,
    val pageSize:Long,
    val paged: Boolean,
    val unpaged: Boolean
    )