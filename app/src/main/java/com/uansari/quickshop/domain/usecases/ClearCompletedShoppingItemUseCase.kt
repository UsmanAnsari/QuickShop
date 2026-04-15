package com.uansari.quickshop.domain.usecases

import com.uansari.quickshop.domain.repository.ShoppingRepository
import jakarta.inject.Inject

class ClearCompletedShoppingItemUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) {
    suspend operator fun invoke() =
        shoppingRepository.clearCompletedShoppingItem()
}
