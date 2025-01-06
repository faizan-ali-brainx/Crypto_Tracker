package com.example.crypto_tracker.data.local.dao

import androidx.room.*
import com.example.crypto_tracker.data.local.entity.CoinNoteEntity
import com.example.crypto_tracker.data.local.entity.UserCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin_notes")
    fun getAllNotes(): Flow<List<CoinNoteEntity>>

    @Query("SELECT * FROM coin_notes WHERE coinId = :coinId")
    suspend fun getNoteById(coinId: Int): CoinNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: CoinNoteEntity)

    @Delete
    suspend fun deleteNote(note: CoinNoteEntity)

    @Query("SELECT * FROM user_coins")
    fun getUserCoins(): Flow<List<UserCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCoin(coin: UserCoinEntity)

    @Delete
    suspend fun deleteUserCoin(coin: UserCoinEntity)
} 