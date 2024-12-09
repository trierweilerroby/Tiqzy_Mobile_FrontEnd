package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import android.app.DatePickerDialog
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun SearchBar(
    currentLocation: String,
    selectedDate: String,
    onLocationChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Location Input Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                TextField(
                    value = currentLocation,
                    onValueChange = onLocationChange,
                    label = { Text("Current Location") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Date Input Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Date Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = if (selectedDate.isEmpty()) "Select Date" else selectedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Show DatePickerDialog when clicked
            if (showDatePicker) {
                DatePickerDialogWrapper(
                    initialDate = selectedDate,
                    onDateSelected = { date ->
                        onDateChange(date)
                        showDatePicker = false
                    },
                    onDismissRequest = {
                        showDatePicker = false
                    }
                )
            }

            // Search Button
            Button(
                onClick = onSearchClick,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DatePickerDialogWrapper(
    initialDate: String,
    onDateSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    ).apply {
        setOnDismissListener { onDismissRequest() }
        show()
    }
}


