package com.example.sahayak

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.tooling.preview.Preview
import com.example.sahayak.ui.theme.SahayakTheme
import android.util.Patterns

class SignupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SahayakTheme {
                SignupScreen()
            }
        }
    }

    @Composable
    fun SignupScreen(modifier: Modifier = Modifier) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPasswordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.cover1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                togglePasswordVisibility = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                togglePasswordVisibility = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (validateInput(email, password, confirmPassword)) {
                        startActivity(Intent(this@SignupActivity, SigninActivitySecond::class.java))
                        finish()
                    } else {
                        // Show error if validation fails
                        Toast.makeText(this@SignupActivity, "Invalid email or passwords don't match", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register", color = Color.White)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Already have an account? Sign In",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { startActivity(Intent(this@SignupActivity, SigninActivity::class.java)) },
                textAlign = TextAlign.Center
            )
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty() && password == confirmPassword
    }

    @Composable
    fun CustomTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        togglePasswordVisibility: (() -> Unit)? = null
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (togglePasswordVisibility != null) {
                    IconButton(onClick = togglePasswordVisibility) {
                        val icon = if (visualTransformation is PasswordVisualTransformation) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                }
            },
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun SignupScreenPreview() {
        SahayakTheme {
            SignupScreen()
        }
    }
}