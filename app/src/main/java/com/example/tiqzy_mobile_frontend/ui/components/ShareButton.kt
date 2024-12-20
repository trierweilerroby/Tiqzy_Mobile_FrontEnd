package com.example.tiqzy_mobile_frontend.ui.components

import android.content.Intent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.Color

@Composable
fun ShareButton(
    eventLink: String,
    icon: ImageVector = Icons.Default.Share,
    contentDescription: String = "Share Event",
    iconColor: Color = Color.Black
) {
    val context = LocalContext.current

    IconButton(onClick = {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain" // Sharing plain text
            putExtra(Intent.EXTRA_TEXT, eventLink) // The link to share
        }
        val chooser = Intent.createChooser(shareIntent, "Share Event")
        context.startActivity(chooser) // Start the share intent
    }) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}
