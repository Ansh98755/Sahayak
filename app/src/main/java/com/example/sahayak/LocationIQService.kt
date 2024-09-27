package com.example.sahayak
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIQService {

    @GET("v1/search.php")
    fun getNearbyHospitals(
        @Query("key") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("q") query: String = "hospital",
        @Query("format") format: String = "json"
    ): Call<List<HospitalResponse>>

    companion object {
        private const val BASE_URL = "https://us1.locationiq.com/"

        fun create(): LocationIQService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(LocationIQService::class.java)
        }
    }
}
