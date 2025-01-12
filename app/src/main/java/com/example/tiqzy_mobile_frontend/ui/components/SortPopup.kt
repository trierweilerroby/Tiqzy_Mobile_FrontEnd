package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel

@Composable
fun SortPopup(
    onDismissRequest: () -> Unit,
    viewModel: EventViewModel // Pass the ViewModel
) {
    val sortOptions = mapOf(
        "Upcoming first" to "upcoming",
        "Price (Lowest to Highest)" to "price_asc",
        "Price (Highest to Lowest)" to "price_desc"
    )

    var selectedOption by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Sort By") },
        text = {
            LazyColumn {
                items(sortOptions.keys.toList()) { option ->
                    SortOptionRow(
                        label = option,
                        isSelected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                            val sortKey = sortOptions[option] ?: ""
                            viewModel.fetchEventsSortedBy(sortKey) // Fetch sorted events
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) { Text("Close") }
        }
    )
}

@Composable
fun SortOptionRow(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}



