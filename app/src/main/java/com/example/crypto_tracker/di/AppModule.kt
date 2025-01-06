package com.example.crypto_tracker.di

import android.app.Application
import androidx.room.Room
import com.example.crypto_tracker.BuildConfig
import com.example.crypto_tracker.data.local.CryptoDatabase
import com.example.crypto_tracker.data.remote.CoinMarketCapApi
import com.example.crypto_tracker.data.repository.CryptoRepositoryImpl
import com.example.crypto_tracker.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(app: Application): CryptoDatabase {
        return Room.databaseBuilder(
            app,
            CryptoDatabase::class.java,
            "crypto.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinMarketCapApi(): CoinMarketCapApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(
        api: CoinMarketCapApi,
        db: CryptoDatabase
    ): CryptoRepository {
        return CryptoRepositoryImpl(api, db.coinDao)
    }
} 