package com.example.sahayak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sahayak.ui.theme.SahayakTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.platform.LocalContext

class SigninActivitySecond : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SahayakTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentUser by remember { mutableStateOf<FirebaseUser?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                errorMessage = "No user is currently logged in."
                Log.d("SigninActivitySecond", "No user is currently logged in.")
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load user information."
            Log.e("SigninActivitySecond", "Failed to load user information", e)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        currentUser?.let {
            Text(
                text = "Welcome to Sahayak App!",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Name: ${it.displayName ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Email: ${it.email ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            IconButton(onClick = { handleLogout(context) }) {
                Icon(imageVector = Icons.Filled.Logout, contentDescription = "Logout")
            }
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.Black
            )
        } ?: run {
            Text(
                text = "No user logged in.",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                color = Color.Red
            )
        }
    }
}

fun handleLogout(context: android.content.Context) {
    try {
        FirebaseAuth.getInstance().signOut()
        // Navigate back to SignInActivity
        val intent = Intent(context, SigninActivity::class.java)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("SigninActivitySecond", "Failed to log out", e)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SahayakTheme {
        MainScreen()
    }
}
