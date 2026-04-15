package com.uansari.quickshop.domain.usecases

import com.uansari.quickshop.domain.model.ShoppingItem
import com.uansari.quickshop.domain.repository.ShoppingRepository
import javax.inject.Inject

class DeleteShoppingItemUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem) =
        shoppingRepository.deleteShoppingItem(shoppingItem)
}