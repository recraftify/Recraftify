package com.dicoding.recraftify.ui.activity.signup

import androidx.lifecycle.ViewModel
import com.dicoding.recraftify.data.Repository

class SignupViewModel(private val repository: Repository): ViewModel() {
    fun register(name: String, username: String, password: String) = repository.register(name, username, password)
}