package com.example.sahayak
data class HospitalResponse(
    val place_id: String = "",  // Default value
    val licence: String = "",
    val osm_type: String = "",
    val osm_id: String = "",
    val boundingbox: List<String> = emptyList(),  // Default value as empty list
    val lat: String = "",
    val lon: String = "",
    val display_name: String = "",
    val classType: String = "",  // Renamed to avoid reserved keyword
    val type: String = "",
    val importance: Double = 0.0,  // Default value
    val icon: String = ""
)
