package com.uansari.quickshop.domain.usecases

import com.uansari.quickshop.domain.model.Filter
import com.uansari.quickshop.domain.repository.ShoppingRepository
import javax.inject.Inject

class GetAllShoppingItemsUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) {
    operator fun invoke(filter: Filter = Filter.ALL) =
        shoppingRepository.getAllShoppingItems()
}