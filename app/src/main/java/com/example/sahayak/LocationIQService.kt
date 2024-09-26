package com.example.sahayak

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIQService {
    @GET("v1/search.php")  // Adjust this endpoint based on the API documentation
    fun getNearbyHospitals(
        @Query("key") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("tag") tag: String = "hospital" // You may need to specify the type of place
    ): Call<List<HospitalResponse>>

    companion object {
        fun create(): LocationIQService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.locationiq.com/")  // Base URL for LocationIQ
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(LocationIQService::class.java)
        }
    }
}
