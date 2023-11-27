package com.dicoding.recraftify.ui.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.preferences.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
    fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}