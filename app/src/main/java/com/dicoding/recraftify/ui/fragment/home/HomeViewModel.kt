package com.dicoding.recraftify.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.preferences.UserModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()
    val _isLoading: LiveData<Boolean> = isLoading

    fun searchRecipe(query:String){
        viewModelScope.launch {
            repository.searchRecipe(query)
        }
    }
   fun getRecipe() = repository.getRecipe()
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}