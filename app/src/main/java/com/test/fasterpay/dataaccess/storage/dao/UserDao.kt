package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.fasterpay.vo.User
import io.reactivex.Observable

@Dao
interface UserDao {
    @Query("select * from User where isLoggedUser = 1")
    fun getLoggedInUserLiveData(): LiveData<User>

    @Query("select * from User where email = :email")
    fun getUserByEmail(email: String): Observable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(user: List<User>)
}