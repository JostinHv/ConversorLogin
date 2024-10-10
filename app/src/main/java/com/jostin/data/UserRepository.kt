package com.jostin.data

import com.jostin.data.User
import com.jostin.data.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user?.password == password
    }

    suspend fun register(email: String, password: String, name: String): Boolean {
        return try {
            userDao.insertUser(User(email, password, name))
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}