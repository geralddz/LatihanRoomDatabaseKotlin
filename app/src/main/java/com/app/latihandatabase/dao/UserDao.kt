package com.app.latihandatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.latihandatabase.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    fun loginUser(email: String, password: String) : LiveData<User>

    @Insert
    fun registerUser(users: User)

    @Delete
    fun delete(users: User)

    @Update
    fun update(users: User)

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun get(uid: String) : User
}