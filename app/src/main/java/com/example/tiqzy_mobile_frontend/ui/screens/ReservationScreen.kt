package com.example.tiqzy_mobile_frontend.ui.screens

import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType


@Composable
fun ReservationScreen(
    eventName: String,
    location: String,
    imageUrl: String,
    basePrice: Double,
    onBackClicked: () -> Unit,
    onBuyClicked: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("Select a Date") }
    var selectedTimeSlot by remember { mutableStateOf("Select a Time") }
    var adults by remember { mutableStateOf(1) }
    var discountCode by remember { mutableStateOf("") }
    val totalPrice = basePrice * adults

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        IconButton(onClick = onBackClicked) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Event Image
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = eventName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Event Title and Location
        Text(text = eventName, style = MaterialTheme.typography.titleLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Place, contentDescription = "Location", tint = Color.Gray)
            Text(text = location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date Selector
        DropdownField(label = "Date", value = selectedDate) { selectedDate = it }

        // Time Slot Selector
        DropdownField(label = "Time slot", value = selectedTimeSlot) { selectedTimeSlot = it }

        // Number of Adults
        NumberPickerField(label = "Adults", value = adults) { adults = it }

        // Discount Code
        TextField(
            value = discountCode,
            onValueChange = { discountCode = it },
            label = { Text("Discount Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Total Price
        Text(
            text = "Total ${"%.2f".format(totalPrice)}€",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buy Button
        Button(
            onClick = onBuyClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

@Composable
fun DropdownField(label: String, value: String, onValueSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf("Option 1", "Option 2", "Option 3").forEach { option ->
                DropdownMenuItem(onClick = {
                    onValueSelected(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun NumberPickerField(label: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (value > 1) onValueChange(value - 1) }) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text(text = value.toString(), style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onValueChange(value + 1) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationScreenPreview() {
    ReservationScreen(
        eventName = "Lovers Canal Cruise",
        location = "Amsterdam Central Station",
        imageUrl = "https://example.com/image.jpg",
        basePrice = 16.95,
        onBackClicked = {},
        onBuyClicked = {}
    )
}
