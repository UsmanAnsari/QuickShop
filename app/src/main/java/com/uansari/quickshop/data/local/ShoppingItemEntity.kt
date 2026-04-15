package com.uansari.quickshop.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo("is_completed") val isCompleted: Boolean,
    @ColumnInfo("created_at") val createdAt: Long,
)



