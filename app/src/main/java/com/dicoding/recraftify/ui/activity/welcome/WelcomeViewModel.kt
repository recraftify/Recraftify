package com.dicoding.recraftify.ui.activity.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.preferences.UserModel

class WelcomeViewModel(private val repository: Repository): ViewModel() {
    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}