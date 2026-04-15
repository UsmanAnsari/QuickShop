package com.uansari.quickshop.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.uansari.quickshop.domain.model.Filter

@Composable
fun EmptyFilterState(
    filter: Filter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (filter) {
                Filter.ALL -> "🛒"
                Filter.ACTIVE -> "✅"
                Filter.COMPLETED -> "📋"
            },
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = when (filter) {
                Filter.ALL -> "Your list is empty"
                Filter.ACTIVE -> "Nothing left to buy!"
                Filter.COMPLETED -> "No completed items yet"
            },
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = when (filter) {
                Filter.ALL -> "Tap Add to get started"
                Filter.ACTIVE -> "All items are checked off"
                Filter.COMPLETED -> "Check off items to see them here"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}