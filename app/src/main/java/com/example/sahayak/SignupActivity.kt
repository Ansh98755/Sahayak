package com.example.sahayak

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sahayak.ui.theme.SahayakTheme

class SignupActivity : ComponentActivity() {

    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("SahayakPrefs", Context.MODE_PRIVATE)

        // Set Compose content
        setContent {
            SahayakTheme {
                SignUpScreen(
                    onSignUpClick = { email, password -> handleSignUp(email, password) }
                )
            }
        }
    }

    private fun handleSignUp(email: String, password: String) {
        // Basic email and password validation
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        // Save email to SharedPreferences
        saveEmailToPrefs(email)

        // Navigate to SigninActivitySecond after successful signup
        val intent = Intent(this, SigninActivitySecond::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish() // Close SignupActivity
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveEmailToPrefs(email: String) {
        sharedPreferences.edit().putString("savedEmail", email).apply()
    }
}

@Composable
fun SignUpScreen(
    onSignUpClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.cover1), // Replace with your background image resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent) // Transparent background to overlay on top of the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 10.dp)
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            BasicTextField(
                value = email,
                onValueChange = { email = it },
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        if (email.isEmpty()) {
                            Text("Enter email", color = Color.Gray)
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        if (password.isEmpty()) {
                            Text("Enter password", color = Color.Gray)
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onSignUpClick(email, password) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Sign Up", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SahayakTheme {
        SignUpScreen(onSignUpClick = { _, _ -> })
    }
}
