package com.example.sahayak

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Blue

class IntermediateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Intermediate Screen Content
            UserStaffSelection(
                onUserClick = { navigateToMainActivity() },
                onStaffClick = { navigateToMainActivity() }
            )
        }
    }

    // Navigation to MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun UserStaffSelection(onUserClick: () -> Unit, onStaffClick: () -> Unit) {
    // Background Image with overlay content
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.covero), // Your background image here
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ensures image fills the screen
            modifier = Modifier.fillMaxSize()
        )

        // Overlay Shade
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA000000)) // Semi-transparent black overlay
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Text "LOGIN AS" in bold and italics
            Text(
                text = "LOGIN AS",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // USER button
            Button(
                onClick = onUserClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Blue)
            ) {
                Text(
                    text = "USER",
                    color = White,
                    fontSize = 18.sp
                )
            }

            // STAFF button
            Button(
                onClick = onStaffClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Blue)
            ) {
                Text(
                    text = "STAFF",
                    color = White,
                    fontSize = 18.sp
                )
            }
        }
    }
}
