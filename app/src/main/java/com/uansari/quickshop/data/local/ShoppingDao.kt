package com.uansari.quickshop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Insert(onConflict = REPLACE)
    suspend fun addShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Update
    suspend fun updateShoppingItem(shoppingItemEntity: ShoppingItemEntity)
    @Delete
    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Query("SELECT * FROM shopping_items ORDER BY created_at ASC ")
    fun getAllShoppingItems(): Flow<List<ShoppingItemEntity>>

    @Query("DELETE FROM shopping_items WHERE is_completed")
    suspend fun clearAllShoppingItems()
}