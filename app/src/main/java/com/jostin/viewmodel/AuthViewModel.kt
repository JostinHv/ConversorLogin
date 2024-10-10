package com.jostin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.data.User
import com.jostin.data.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            if (result) {
                _currentUser.value = repository.getUserByEmail(email)
            }
            onResult(result)
        }
    }

    fun register(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.register(email, password, name)
            onResult(result)
        }
    }

    fun logout() {
        _currentUser.value = null
    }
}