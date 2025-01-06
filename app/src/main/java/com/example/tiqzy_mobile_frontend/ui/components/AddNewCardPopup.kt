package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddNewCardPopup(
    onClose: () -> Unit,
    onConfirm: (cardNumber: String, year: String, month: String, cvv: String, cardName: String) -> Unit
) {
    val cardNumber = remember { mutableStateOf("") }
    val year = remember { mutableStateOf("") }
    val month = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }
    val cardName = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onClose() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title and Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add new card",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )

                IconButton(onClick = { onClose() }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Close"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Card details *",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Number
            OutlinedTextField(
                value = cardNumber.value,
                onValueChange = { cardNumber.value = it },
                placeholder = { Text("Card number") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Expiry Date and CVV
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = year.value,
                    onValueChange = { year.value = it },
                    placeholder = { Text("Year") },
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = month.value,
                    onValueChange = { month.value = it },
                    placeholder = { Text("Month") },
                    modifier = Modifier.weight(1f).padding(start = 8.dp, end = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = cvv.value,
                    onValueChange = { cvv.value = it },
                    placeholder = { Text("CVV") },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Name on card  optional",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Name on Card
            OutlinedTextField(
                value = cardName.value,
                onValueChange = { cardName.value = it },
                placeholder = { Text("Card name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = {
                    onConfirm(
                        cardNumber.value,
                        year.value,
                        month.value,
                        cvv.value,
                        cardName.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF5B82D1))
            ) {
                Text(text = "Confirm", color = Color.White)
            }
        }
    }
}

@Composable
@Preview
fun AddNewCardPopupPreview() {
    AddNewCardPopup(
        onClose = {},
        onConfirm = { _, _, _, _, _ -> }
    )
}
