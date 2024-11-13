package com.example.sahayak

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sahayak.ui.theme.SahayakTheme

class HospitalDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hospitalName = intent.getStringExtra("hospitalName") ?: "Hospital Details"
        val bedAvailability = sampleBedData()

        setContent {
            SahayakTheme {
                HospitalDetailsScreen(hospitalName = hospitalName, bedAvailability = bedAvailability)
            }
        }
    }

    private fun sampleBedData(): List<BedSection> {
        return listOf(
            BedSection("General Ward", 10),
            BedSection("ICU", 2),
            BedSection("Private Rooms", 5),
            BedSection("Emergency", 0)
        )
    }
}

data class BedSection(val sectionName: String, val availableBeds: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalDetailsScreen(hospitalName: String, bedAvailability: List<BedSection>) {
    var showBookingDialog by remember { mutableStateOf(false) }
    var selectedSection by remember { mutableStateOf<BedSection?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(hospitalName) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Bed Availability",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn {
                    items(bedAvailability.size) { index ->
                        val section = bedAvailability[index]
                        BedAvailabilityItem(
                            section = section,
                            onBookClick = {
                                selectedSection = section
                                showBookingDialog = true
                            }
                        )
                    }
                }

                if (showBookingDialog && selectedSection != null) {
                    BookingDialog(
                        section = selectedSection!!,
                        onConfirm = {
                            Toast.makeText(
                                LocalContext.current,
                                "Bed booked in ${selectedSection!!.sectionName}",
                                Toast.LENGTH_SHORT
                            ).show()
                            showBookingDialog = false
                        },
                        onCancel = { showBookingDialog = false }
                    )
                }
            }
        }
    )
}

@Composable
fun BedAvailabilityItem(section: BedSection, onBookClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = section.sectionName, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Available Beds: ${section.availableBeds}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (section.availableBeds > 0) Color.Green else Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onBookClick,
            enabled = section.availableBeds > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (section.availableBeds > 0) MaterialTheme.colorScheme.primary else Color.Gray
            )
        ) {
            Text(text = "Book Bed")
        }
    }
}

@Composable
fun BookingDialog(section: BedSection, onConfirm: @Composable () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Booking Confirmation") },
        text = { Text("Are you sure you want to book a bed in ${section.sectionName}?") },
        confirmButton = {
            Button(onClick = onConfirm as () -> Unit) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SahayakTheme {
        HospitalDetailsScreen(
            hospitalName = "Sample Hospital",
            bedAvailability = listOf(
                BedSection("General Ward", 10),
                BedSection("ICU", 2)
            )
        )
    }
}
