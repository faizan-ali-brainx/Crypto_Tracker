package com.example.crypto_tracker.domain.repository

import com.example.crypto_tracker.data.local.entity.UserCoinEntity
import com.example.crypto_tracker.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    suspend fun getTopCoins(): List<Coin>
    fun getCoinNotes(): Flow<Map<Int, String>>
    suspend fun updateCoinNote(coinId: Int, note: String)
    fun getUserCoins(): Flow<List<UserCoinEntity>>
    suspend fun addUserCoin(symbol: String)
    suspend fun removeUserCoin(symbol: String)
} 