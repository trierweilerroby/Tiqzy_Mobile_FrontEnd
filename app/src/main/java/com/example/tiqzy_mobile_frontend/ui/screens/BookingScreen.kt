package com.example.tiqzy_mobile_frontend.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tiqzy_mobile_frontend.data.model.Event
import com.example.tiqzy_mobile_frontend.data.repository.OrderRepository
import com.example.tiqzy_mobile_frontend.viewmodel.AuthViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.EventViewModel
import com.example.tiqzy_mobile_frontend.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navController: NavHostController,
    eventId: Int,
    eventViewModel: EventViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val event by eventViewModel.selectedEvent.collectAsState()
    var selectedDate by remember { mutableStateOf("") }
    var selectedTimeSlot by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var adultCount by remember { mutableStateOf(1) }
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showPopup by remember { mutableStateOf(false) } // State for showing the popup

    val userId = authViewModel.getCurrentUserId()

    // Fetch the event when the screen is loaded
    LaunchedEffect(eventId) {
        eventViewModel.fetchEventById(eventId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Event Details
                    AsyncImage(
                        model = event?.imageUrl,
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                    Text(
                        text = event?.title ?: "Event Title",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = event?.venue?.address ?: "Event Location",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Date Picker
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Date") },
                        trailingIcon = {
                            val context = LocalContext.current
                            IconButton(onClick = {
                                val calendar = Calendar.getInstance()
                                DatePickerDialog(
                                    context,
                                    { _, year, month, day -> selectedDate = "$year-${month + 1}-$day" },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }) {
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Pick Date")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Time Slot Dropdown
                    Box {
                        OutlinedTextField(
                            value = selectedTimeSlot,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Time Slot") },
                            trailingIcon = {
                                IconButton(onClick = { isDropdownExpanded = true }) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Pick Time Slot")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            listOf("10:00:00 - 12:00:00", "12:30:00 - 15:00:00", "17:30:00 - 20:00:00").forEach { time ->
                                DropdownMenuItem(
                                    text = { Text(time) },
                                    onClick = {
                                        selectedTimeSlot = time
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Adults Counter
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Adults", style = MaterialTheme.typography.bodyMedium)

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { if (adultCount > 1) adultCount-- }) {
                                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
                            }
                            Text(
                                text = "$adultCount",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { adultCount++ }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                            }
                        }
                    }
                }

                // Total Price and Buy Button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val totalPrice = (event?.price?.toDouble() ?: 0.0) * adultCount
                    Text(
                        text = "Total: €${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Button(
                        onClick = {
                            if (userId == null) {
                                errorMessage = "Please log in to proceed."
                                return@Button
                            }

                            isProcessing = true
                            orderViewModel.addTicket(
                                name = event?.title ?: "Unknown Event",
                                date = selectedDate,
                                location = event?.venue?.address ?: "Unknown Location",
                                imageUrl = event?.imageUrl ?: "",
                                timeframe = "${selectedDate} ${selectedTimeSlot}",
                                userId = userId,
                                onSuccess = {
                                    isProcessing = false
                                    showPopup = true // Show popup on success
                                },
                                onError = {
                                    isProcessing = false
                                    errorMessage = it.message ?: "An error occurred."
                                }
                            )
                        },
                        enabled = selectedDate.isNotEmpty() && selectedTimeSlot.isNotEmpty() && !isProcessing,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isProcessing) "Processing..." else "Buy")
                    }
                }
            }

            // Popup Overlay
            if (showPopup) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Ticket successfully added!",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(3000) // Popup duration: 3 seconds
                    showPopup = false
                }
            }
        }
    }
}
