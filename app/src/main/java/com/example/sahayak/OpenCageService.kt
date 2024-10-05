package com.example.sahayak

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenCageService {
    @GET("geocode/v1/json")
    fun getNearbyHospitals(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("countrycode") countryCode: String
    ): Call<OpenCageResponse>
}
