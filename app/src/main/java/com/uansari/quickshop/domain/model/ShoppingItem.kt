package com.uansari.quickshop.domain.model

data class ShoppingItem(
    val id: Int = 0,
    val name: String,
    val isCompleted: Boolean = false,
    val createdAt: Long,
)
