package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.vo.User

@Dao
interface UserDao {
    @Query("select * from User where isLoggedUser = 1")
    fun getLoggedInUserLiveData(): LiveData<User>

    @Query("select * from User where isLoggedUser = 1")
    suspend fun getLoggedInUserSync(): User

    @Query("select * from User where email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(user: List<User>)
}