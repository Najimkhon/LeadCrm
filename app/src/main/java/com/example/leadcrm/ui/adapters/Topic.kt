package com.example.leadcrm.ui.adapters

data class Topic(
    val id: Long,
    val name: String,
    val displayOrder: Int,

    val isVerified : Boolean = false,
    val isLocked: Boolean = false,

    var isSelected : Boolean = false,
)