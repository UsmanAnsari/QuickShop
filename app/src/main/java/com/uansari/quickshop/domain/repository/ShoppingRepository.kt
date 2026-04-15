package com.uansari.quickshop.domain.repository

import com.uansari.quickshop.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getAllShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun addShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    suspend fun toggleShoppingItem(shoppingItem: ShoppingItem)
    suspend fun clearCompletedShoppingItem()
}