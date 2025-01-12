package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.OnboardingViewModel

@Composable
fun FilterPopup(
    onDismissRequest: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    // Observe categories from the ViewModel
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Filter By") },
        text = {
            Column {
                // Example Filter Options
                Text("Event Categories")
                categories.forEach { category ->
                    CheckboxRow(label = category.name)
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) { Text("Show Results") }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) { Text("Cancel") }
        }
    )
}

@Composable
fun CheckboxRow(label: String) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(text = label)
    }
}


@Preview(showBackground = true)
@Composable
fun FilterPopupPreview() {
    FilterPopup(onDismissRequest = {})
}
