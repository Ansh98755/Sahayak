package com.example.sahayak

data class OpenCageResponse(
    val results: List<Result>
) {
    data class Result(
        val formatted: String,
        val components: Components,
        val geometry: Geometry
    ) {
        data class Components(
            val road: String?,
            val city: String?,
            val country: String?
        )

        data class Geometry(
            val lat: Double,
            val lng: Double
        )
    }
}
