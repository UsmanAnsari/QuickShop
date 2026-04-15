package com.uansari.quickshop.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uansari.quickshop.domain.model.Filter

@Composable
fun FilterChipRow(
    selectedFilter: Filter,
    onFilterSelected: (Filter) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Filter.entries.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = when (filter) {
                            Filter.ALL -> "All"
                            Filter.ACTIVE -> "Active"
                            Filter.COMPLETED -> "Completed"
                        }
                    )
                }
            )
        }
    }
}