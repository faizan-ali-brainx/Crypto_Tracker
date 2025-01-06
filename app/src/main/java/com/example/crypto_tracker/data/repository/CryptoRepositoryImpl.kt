package com.example.crypto_tracker.data.repository

import android.util.Log
import com.example.crypto_tracker.data.local.dao.CoinDao
import com.example.crypto_tracker.data.local.entity.CoinNoteEntity
import com.example.crypto_tracker.data.local.entity.UserCoinEntity
import com.example.crypto_tracker.data.remote.CoinMarketCapApi
import com.example.crypto_tracker.domain.model.Coin
import com.example.crypto_tracker.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CoinMarketCapApi,
    private val dao: CoinDao
) : CryptoRepository {

    override suspend fun getTopCoins(): List<Coin> {
        return try {
            Log.d("tracking_api", "API is called")
            api.getLatestListings(limit = 10).data.map { coinData ->
                Coin(
                    id = coinData.id,
                    name = coinData.name,
                    symbol = coinData.symbol,
                    price = coinData.quote.USD.price,
                    percentChange24h = coinData.quote.USD.percent_change_24h
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getCoinNotes(): Flow<Map<Int, String>> {
        return dao.getAllNotes().map { notes ->
            notes.associate { it.coinId to it.note }
        }
    }

    override suspend fun updateCoinNote(coinId: Int, note: String) {
        dao.insertNote(CoinNoteEntity(coinId, note))
    }

    override fun getUserCoins(): Flow<List<UserCoinEntity>> {
        return dao.getUserCoins()
    }

    override suspend fun addUserCoin(symbol: String) {
        dao.insertUserCoin(UserCoinEntity(symbol))
    }

    override suspend fun removeUserCoin(symbol: String) {
        dao.deleteUserCoin(UserCoinEntity(symbol))
    }
} 