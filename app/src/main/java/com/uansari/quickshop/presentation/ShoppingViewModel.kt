package com.uansari.quickshop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uansari.quickshop.domain.model.Filter
import com.uansari.quickshop.domain.model.ShoppingItem
import com.uansari.quickshop.domain.usecases.AddShoppingItemUseCase
import com.uansari.quickshop.domain.usecases.ClearCompletedShoppingItemUseCase
import com.uansari.quickshop.domain.usecases.DeleteShoppingItemUseCase
import com.uansari.quickshop.domain.usecases.GetAllShoppingItemsUseCase
import com.uansari.quickshop.domain.usecases.ToggleShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val getAllShoppingItemsUseCase: GetAllShoppingItemsUseCase,
    private val clearAllShoppingItemsUseCase: ClearCompletedShoppingItemUseCase,
    private val toggleShoppingItemUseCase: ToggleShoppingItemUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<ShoppingUiEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeItems()
    }


    fun onIntent(intent: ShoppingIntent) {
        when (intent) {
            is ShoppingIntent.AddItem -> handleAddItem(intent.name)
            is ShoppingIntent.ToggleItem -> handleToggleItem(intent.item)
            is ShoppingIntent.DeleteItem -> handleDeleteItem(intent.item)
            is ShoppingIntent.SetFilter -> handleSetFilter(intent.filter)
            is ShoppingIntent.UpdateInput -> handleUpdateInput(intent.text)
            is ShoppingIntent.ClearCompleted -> handleClearCompleted()
        }
    }

    private fun observeItems() {
        viewModelScope.launch {
            getAllShoppingItemsUseCase().collect { items ->
                val currentFilter = _uiState.value.selectedFilter
                _uiState.update {
                    it.copy(
                        allItems = items,
                        filteredItems = applyFilter(items, currentFilter),
                        activeCount = items.count { item -> !item.isCompleted },
                        completedCount = items.count { item -> item.isCompleted },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun handleAddItem(name: String) {
        viewModelScope.launch {
            addShoppingItemUseCase(name)
            _uiState.update { it.copy(inputText = "") }
        }
    }

    private fun handleToggleItem(item: ShoppingItem) {
        viewModelScope.launch {
            toggleShoppingItemUseCase(item)
        }
    }

    private fun handleDeleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            deleteShoppingItemUseCase(item)
            _effect.send(ShoppingUiEffect.ShowSnackbar("${item.name} removed"))
        }
    }

    private fun handleSetFilter(filter: Filter) {
        _uiState.update {
            it.copy(
                selectedFilter = filter, filteredItems = applyFilter(it.allItems, filter)
            )
        }
    }

    private fun handleUpdateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    private fun handleClearCompleted() {
        viewModelScope.launch {
            clearAllShoppingItemsUseCase()
            _effect.send(ShoppingUiEffect.ShowSnackbar("Completed items cleared"))
        }
    }

    private fun applyFilter(items: List<ShoppingItem>, filter: Filter): List<ShoppingItem> =
        when (filter) {
            Filter.ALL -> items
            Filter.ACTIVE -> items.filter { !it.isCompleted }
            Filter.COMPLETED -> items.filter { it.isCompleted }
        }
}