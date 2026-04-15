package com.uansari.quickshop.data.repository

import com.uansari.quickshop.data.local.ShoppingDao
import com.uansari.quickshop.data.local.ShoppingItemEntity
import com.uansari.quickshop.domain.model.ShoppingItem
import com.uansari.quickshop.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao
) : ShoppingRepository {
    override fun getAllShoppingItems(): Flow<List<ShoppingItem>> {
        return shoppingDao.getAllShoppingItems().map { shoppingList ->
            shoppingList.map { it.toDomain() }
        }
    }

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.addShoppingItem(shoppingItem.toEntity())
    }


    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem.toEntity())
    }

    /*
        override suspend fun toggleShoppingItem(id: Int) {
            TODO("Not yet implemented")
        }
    override suspend fun clearCompletedShoppingItem() {
            TODO("Not yet implemented")
    }
    */


    private fun ShoppingItemEntity.toDomain(): ShoppingItem {
        return ShoppingItem(
            id = id, name = name, isCompleted = isCompleted, createdAt = createdAt
        )
    }

    private fun ShoppingItem.toEntity(): ShoppingItemEntity {
        return ShoppingItemEntity(
            id = id, name = name, isCompleted = isCompleted, createdAt = createdAt
        )
    }
}