package com.example.crypto_tracker.domain.model

data class Coin(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val percentChange24h: Double,
    val userNote: String = ""
) 