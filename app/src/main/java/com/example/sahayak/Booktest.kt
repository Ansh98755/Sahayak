package com.example.sahayak

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.sahayak.ui.theme.SahayakTheme
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Booktest : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val hospitalsState = mutableStateOf(emptyList<HospitalResponse>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            SahayakTheme {
                HospitalListScreen(hospitals = hospitalsState.value)
            }
        }

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            requestLocationUpdates()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestLocationUpdates()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (location in locationResult.locations) {
                fetchNearbyHospitals(location.latitude, location.longitude)
            }
        }
    }

    private fun fetchNearbyHospitals(latitude: Double, longitude: Double) {
        val call = NominatimService.create().getNearbyHospitals("hospital", latitude = latitude, longitude = longitude)
        call.enqueue(object : Callback<List<HospitalResponse>> {
            override fun onResponse(call: Call<List<HospitalResponse>>, response: Response<List<HospitalResponse>>) {
                Log.d("API Response", response.raw().toString())

                if (response.isSuccessful) {
                    hospitalsState.value = response.body() ?: emptyList()
                } else {
                    Log.e("API Error", response.errorBody()?.string() ?: "Unknown error")
                    Toast.makeText(this@Booktest, "Error fetching hospitals: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<HospitalResponse>>, t: Throwable) {
                Toast.makeText(this@Booktest, "Failed to fetch hospitals: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API Error", t.message.toString())
            }
        })
    }

    @Composable
    fun HospitalListScreen(hospitals: List<HospitalResponse>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Hospitals near you:",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            hospitals.forEach { hospital ->
                HospitalItem(hospital)
            }
        }
    }

    @Composable
    fun HospitalItem(hospital: HospitalResponse) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .clickable {
                    showBookingDialog(hospital)
                }
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(text = hospital.display_name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Available Beds: ${hospital.availability}", style = MaterialTheme.typography.bodyMedium)
        }
    }

    private fun showBookingDialog(hospital: HospitalResponse) {
        val availability = hospital.availability
        if (availability > 0) {
            hospitalsState.value = hospitalsState.value.map {
                if (it == hospital) it.copy(availability = availability - 1) else it
            }
            Toast.makeText(this, "Successfully booked a bed at ${hospital.display_name}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No beds available at ${hospital.display_name}", Toast.LENGTH_SHORT).show()
        }
    }
}
