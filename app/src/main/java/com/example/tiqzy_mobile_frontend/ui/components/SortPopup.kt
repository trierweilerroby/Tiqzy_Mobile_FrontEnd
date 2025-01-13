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
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel

@Composable
fun SortPopup(
    onDismissRequest: () -> Unit,
    viewModel: EventViewModel
) {
    val sortOptions = mapOf(
        "Upcoming first" to "upcoming",
        "Price (Lowest to Highest)" to "price_asc",
        "Price (Highest to Lowest)" to "price_desc"
    )

    var selectedOption by remember { mutableStateOf("") }
    var selectedSortKey by remember { mutableStateOf("") }

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
                            selectedSortKey = sortOptions[option] ?: ""
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedSortKey.isNotEmpty()) {
                        println("Applying sort: $selectedSortKey")
                        viewModel.updateSortKey(selectedSortKey)
                        viewModel.fetchEventsSortedBy(selectedSortKey)
                    }
                    onDismissRequest() // Dismiss the dialog
                }
            ) {
                Text("Apply Sort")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) { Text("Cancel") }
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



