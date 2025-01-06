package com.example.crypto_tracker.data.remote

import com.example.crypto_tracker.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinMarketCapApi {
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getLatestListings(
        @Header("X-CMC_PRO_API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int = 10,
        @Query("convert") convert: String = "USD"
    ): CoinListingsResponse
}

data class CoinListingsResponse(
    val data: List<CoinData>
)

data class CoinData(
    val id: Int,
    val name: String,
    val symbol: String,
    val quote: Quote
)

data class Quote(
    val USD: UsdData
)

data class UsdData(
    val price: Double,
    val percent_change_24h: Double
) 