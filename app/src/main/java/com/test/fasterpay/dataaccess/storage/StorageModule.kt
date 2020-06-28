package com.test.fasterpay.dataaccess.storage

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.test.fasterpay.dataaccess.storage.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StorageModule {
    @Provides @Singleton
    fun provideDatabase(application: Application): FasterPayDatabase {
        return Room
            .databaseBuilder(application, FasterPayDatabase::class.java, "faster_pay_db")
            .build()
    }

    @Provides
    fun provideUserDao(database: FasterPayDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideWalletDao(database: FasterPayDatabase): WalletDao {
        return database.wallerDao()
    }

    @Provides
    fun provideTransactionsDao(database: FasterPayDatabase): TransactionDao {
        return database.pastTransactionDao()
    }

    @Provides
    fun provideFutureTransactionDao(database: FasterPayDatabase): FutureTransactionDao {
        return database.futureTransactionDao()
    }

    @Provides
    fun provideCredentialsDao(database: FasterPayDatabase): CredentialsDao {
        return database.credentialsDao()
    }

    @Provides @Singleton
    fun provideSharedPrefs(application: Application): SharedPreferences{
        return application.getSharedPreferences("faster_pay", Application.MODE_PRIVATE)
    }
}