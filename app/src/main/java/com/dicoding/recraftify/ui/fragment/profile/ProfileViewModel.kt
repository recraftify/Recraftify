package com.dicoding.recraftify.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.ProfileResponse
import com.dicoding.recraftify.setting.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    private val _profileState: MutableLiveData<ResultState<ProfileResponse>> = MutableLiveData()
    val profileState: LiveData<ResultState<ProfileResponse>> get() = _profileState

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _profileState.postValue(ResultState.Loading)
            _profileState.postValue(repository.profile())
        }
    }
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}