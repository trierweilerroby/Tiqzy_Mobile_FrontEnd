package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PaymentOptionList(onOptionSelected: (String) -> Unit) {
    Column {
        PaymentOption(
            text = "Credit & Debit Cards",
            icon = android.R.drawable.ic_menu_camera,
            onClick = { onOptionSelected("Credit & Debit Cards") }
        )
        PaymentOption(
            text = "Wallet",
            icon = android.R.drawable.ic_menu_myplaces,
            onClick = { onOptionSelected("Wallet") }
        )
        PaymentOption(
            text = "Net Banking",
            icon = android.R.drawable.ic_menu_slideshow,
            onClick = { onOptionSelected("Net Banking") }
        )
    }
}

@Composable
fun PaymentOption(text: String, icon: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = Color(0xFF4A4A4A),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = android.R.drawable.ic_media_next),
            contentDescription = null,
            tint = Color(0xFF4A4A4A),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview
fun PaymentOptionListPreview() {
    PaymentOptionList(onOptionSelected = {})
}
