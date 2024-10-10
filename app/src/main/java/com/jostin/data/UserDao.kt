package com.jostin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jostin.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert
    suspend fun insertUser(user: User)
}