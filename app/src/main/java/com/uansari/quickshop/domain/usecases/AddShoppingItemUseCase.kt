package com.uansari.quickshop.domain.usecases

import com.uansari.quickshop.domain.model.ShoppingItem
import com.uansari.quickshop.domain.repository.ShoppingRepository
import javax.inject.Inject

class AddShoppingItemUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) {
    suspend operator fun invoke(shoppingItemName: String) {
        if (shoppingItemName.isBlank()) return
        shoppingRepository.addShoppingItem(
            ShoppingItem(
                name = shoppingItemName,
                createdAt = System.currentTimeMillis(),
            )
        )
    }
}