package com.uansari.quickshop.presentation

import com.uansari.quickshop.domain.model.Filter
import com.uansari.quickshop.domain.model.ShoppingItem

data class ShoppingUiState(
    val allItems: List<ShoppingItem> = emptyList(),
    val filteredItems: List<ShoppingItem> = emptyList(),
    val activeCount: Int = 0,
    val completedCount: Int = 0,
    val selectedFilter: Filter = Filter.ALL,
    val inputText: String = "",
    val isLoading: Boolean = true
)

sealed class ShoppingIntent {
    data class AddItem(val name: String) : ShoppingIntent()
    data class ToggleItem(val item: ShoppingItem) : ShoppingIntent()
    data class DeleteItem(val item: ShoppingItem) : ShoppingIntent()
    data class SetFilter(val filter: Filter) : ShoppingIntent()
    data class UpdateInput(val text: String) : ShoppingIntent()
    data object ClearCompleted : ShoppingIntent()
}

sealed class ShoppingUiEffect {
    data class ShowSnackbar(val message: String) : ShoppingUiEffect()
}