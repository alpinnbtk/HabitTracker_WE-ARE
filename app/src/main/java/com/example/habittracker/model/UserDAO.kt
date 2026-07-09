package com.example.habittracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?
}