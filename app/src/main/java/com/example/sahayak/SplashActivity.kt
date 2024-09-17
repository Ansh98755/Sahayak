package com.example.sahayak
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//import androidx.compose.material.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.sahayak.MainActivity
import com.example.sahayak.R  // Correct import
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen {
                navigateToIntermediateActivity()
            }
        }
    }

    private fun navigateToIntermediateActivity() {
        startActivity(Intent(this, IntermediateActivity::class.java))
        finish()
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Use LaunchedEffect to navigate after a delay
    LaunchedEffect(Unit) {
        delay(4000)  // 4 seconds delay
        onTimeout()
    }

    // Layout for the splash screen with Lottie animation and text
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Center content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),  // Center alignment
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bookbed)).value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                iterations = LottieConstants.IterateForever
            )
        }

        // Bottom text
        Text(
            text = "Book Your Bed",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)  // Align text to the bottom center

        )
    }
}
