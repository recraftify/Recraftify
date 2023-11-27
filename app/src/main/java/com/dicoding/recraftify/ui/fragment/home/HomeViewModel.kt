package com.dicoding.recraftify.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.preferences.UserModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}