import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.sahayak.OpenCageService


object RetrofitInstance {
    private const val BASE_URL = "https://api.opencagedata.com/"  // Correct base URL for OpenCage API

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OpenCageService by lazy {
        retrofit.create(OpenCageService::class.java)
    }
}
