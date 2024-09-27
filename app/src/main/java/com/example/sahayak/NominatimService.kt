package com.example.sahayak

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimService {
    @GET("search")
    fun getNearbyHospitals(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 10,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Call<List<HospitalResponse>>

    companion object {
        private const val BASE_URL = "https://nominatim.openstreetmap.org/"

        fun create(): NominatimService {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()

            return retrofit.create(NominatimService::class.java)
        }
    }
}
