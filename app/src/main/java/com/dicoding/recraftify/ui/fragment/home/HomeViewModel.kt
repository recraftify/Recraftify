package com.dicoding.recraftify.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.preferences.UserModel

class HomeViewModel(private val repository: Repository): ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()
    val _isLoading: LiveData<Boolean> = isLoading

   fun getRecipe() = repository.getRecipe()
    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}