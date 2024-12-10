package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiqzy_mobile_frontend.R

@Composable
fun AppleAndGoogle(
    onAppleLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = Color.Gray.copy(alpha = 0.2F)
            )
            Text(
                text = "Or",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray.copy(alpha = 0.6F)
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = Color.Gray.copy(alpha = 0.2F)
            )
        }

        // Apple Login Button
        OutlinedButton(
            onClick = onAppleLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "Apple Logo",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log in with Apple")
        }

        // Google Login Button
        OutlinedButton(
            onClick = onGoogleLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log in with Google")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Signup Navigation
        TextButton(
            onClick = onSignUpClick
        ) {
            Text(
                text = "Don’t have an account? Sign Up",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
