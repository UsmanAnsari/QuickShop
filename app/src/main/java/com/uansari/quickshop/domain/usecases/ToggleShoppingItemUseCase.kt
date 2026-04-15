package com.uansari.quickshop.domain.usecases

import com.uansari.quickshop.domain.model.ShoppingItem
import com.uansari.quickshop.domain.repository.ShoppingRepository
import jakarta.inject.Inject

class ToggleShoppingItemUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) {
    suspend operator fun invoke(item: ShoppingItem) {
        shoppingRepository.toggleShoppingItem(item.copy(isCompleted = !item.isCompleted))
    }
}
