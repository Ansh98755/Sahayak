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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
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
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            SahayakTheme {
                SignUpScreen(
                    onSignUpClick = { email, password -> handleSignUp(email, password) }
                )
            }
        }
    }

    private fun handleSignUp(email: String, password: String) {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveEmailToPrefs(email)
                    val intent = Intent(this, SigninActivitySecond::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Sign-Up failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveEmailToPrefs(email: String) {
        val sharedPreferences = getSharedPreferences("SahayakPrefs", MODE_PRIVATE)
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
        Image(
            painter = painterResource(id = R.drawable.cover1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
//                .background(Color.Transparent) // Transparent background to overlay on top of the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // Ensure this resource exists
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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = true
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
