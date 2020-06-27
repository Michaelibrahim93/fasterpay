package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CredentialsDao {
    @Query("select * from CredentialsForm where email = :email")
    fun getCredentialsByEmail(email: String): Single<CredentialsForm>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCredentials(credentialsForm: CredentialsForm): Completable

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCredentialsList(list: List<CredentialsForm>): Completable
}