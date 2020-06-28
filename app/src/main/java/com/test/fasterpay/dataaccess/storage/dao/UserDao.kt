package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.vo.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("select * from User where isLoggedUser = 1")
    fun getLoggedInUserLiveData(): LiveData<User>

    @Query("select * from User where isLoggedUser = 1")
    fun getLoggedInUserSync(): User

    @Query("select * from User where email = :email")
    fun getUserByEmail(email: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(user: List<User>): Completable
}