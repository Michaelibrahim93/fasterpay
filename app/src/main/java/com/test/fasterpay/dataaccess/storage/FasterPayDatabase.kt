package com.test.fasterpay.dataaccess.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.storage.converters.ObjectsConverter
import com.test.fasterpay.dataaccess.storage.dao.*
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.PastTransaction
import com.test.fasterpay.vo.User
import com.test.fasterpay.vo.Wallet

@Database(
    entities = [User::class, PastTransaction::class, Wallet::class, CredentialsForm::class, MoneyTransaction::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ObjectsConverter::class)
abstract class FasterPayDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun wallerDao(): WalletDao
    abstract fun pastTransactionDao(): TransactionDao
    abstract fun futureTransactionDao(): FutureTransactionDao
    abstract fun credentialsDao(): CredentialsDao
}