package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel

@Composable
fun FilterPopup(
    onDismissRequest: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
    preSelectedCategories: List<String> = emptyList(),
    onApplyFilters: (List<String>) -> Unit
) {
    // Observe categories from the ViewModel
    val categories by viewModel.categories.collectAsState(initial = emptyList())
    val selectedCategories = remember {
        mutableStateListOf<String>().apply { addAll(preSelectedCategories) }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Filter By") },
        text = {
            Column {
                Text("Event Categories")
                categories.forEach { category ->
                    CheckboxRow(
                        label = category.name,
                        isChecked = selectedCategories.contains(category.name),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedCategories.add(category.name)
                            } else {
                                selectedCategories.remove(category.name)
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onApplyFilters(selectedCategories)
                    onDismissRequest()
                }
            ) { Text("Show Results") }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) { Text("Cancel") }
        }
    )
}

@Composable
fun CheckboxRow(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
