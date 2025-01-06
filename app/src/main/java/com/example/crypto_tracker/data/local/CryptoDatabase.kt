package com.example.crypto_tracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crypto_tracker.data.local.dao.CoinDao
import com.example.crypto_tracker.data.local.entity.CoinNoteEntity
import com.example.crypto_tracker.data.local.entity.UserCoinEntity

@Database(
    entities = [CoinNoteEntity::class, UserCoinEntity::class],
    version = 1
)
abstract class CryptoDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
} 