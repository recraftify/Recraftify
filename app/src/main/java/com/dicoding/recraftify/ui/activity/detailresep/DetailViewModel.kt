package com.dicoding.recraftify.ui.activity.detailresep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.WasteIdResponse
import com.dicoding.recraftify.setting.ResultState
import kotlinx.coroutines.launch

class DetailViewModel(private var repository: Repository) : ViewModel() {
    private val _recipeDetail = MutableLiveData<ResultState<WasteIdResponse>>()
    val recipeDetail: LiveData<ResultState<WasteIdResponse>> = _recipeDetail

    fun getRecipeById(id: String) {
        viewModelScope.launch {
            try {
                _recipeDetail.value = ResultState.Loading
                val response = repository.getRecipeById(id)
                _recipeDetail.value = ResultState.Success(response)
            } catch (e: Exception) {
                _recipeDetail.value = ResultState.Error(e.message ?: "Unknown error")
            }
        }
    }
}


