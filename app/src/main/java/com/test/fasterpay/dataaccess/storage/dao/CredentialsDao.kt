package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm

@Dao
interface CredentialsDao {
    @Query("select * from CredentialsForm where email = :email")
    suspend fun getCredentialsByEmail(email: String): CredentialsForm?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCredentials(credentialsForm: CredentialsForm)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCredentialsList(list: List<CredentialsForm>)
}