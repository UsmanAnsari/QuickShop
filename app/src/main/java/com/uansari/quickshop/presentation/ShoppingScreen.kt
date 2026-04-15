package com.uansari.quickshop.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uansari.quickshop.presentation.components.AddItemInput
import com.uansari.quickshop.presentation.components.EmptyFilterState
import com.uansari.quickshop.presentation.components.FilterChipRow
import com.uansari.quickshop.presentation.components.ShoppingItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShoppingUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("QuickShop") },
        )
    }, snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AddItemInput(
                value = uiState.inputText,
                onValueChange = { viewModel.onIntent(ShoppingIntent.UpdateInput(it)) },
                onAdd = { viewModel.onIntent(ShoppingIntent.AddItem(uiState.inputText)) })

            FilterChipRow(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { viewModel.onIntent(ShoppingIntent.SetFilter(it)) })

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                uiState.filteredItems.isEmpty() -> {
                    EmptyFilterState(
                        filter = uiState.selectedFilter, modifier = Modifier.weight(1f)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = uiState.filteredItems, key = { it.id }) { item ->
                            ShoppingItemRow(
                                item = item,
                                onToggle = { viewModel.onIntent(ShoppingIntent.ToggleItem(item)) },
                                onDelete = { viewModel.onIntent(ShoppingIntent.DeleteItem(item)) })
                        }
                    }
                }
            }

            if (uiState.completedCount > 0) {
                Button(
                    onClick = { viewModel.onIntent(ShoppingIntent.ClearCompleted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Clear Completed (${uiState.completedCount})")
                }
            }
        }
    }
}