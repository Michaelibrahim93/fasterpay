package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import io.reactivex.Observable

@Dao
interface CredentialsDao {
    @Query("select * from CredentialsForm where email = :email")
    fun getCredentialsByEmail(email: String): Observable<CredentialsForm>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCredentials(credentialsForm: CredentialsForm): Observable<Void>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCredentialsList(list: List<CredentialsForm>): Observable<Void>
}