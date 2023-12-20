package com.dicoding.recraftify.ui.activity.result


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.HistoryIdResponse
import com.dicoding.recraftify.data.response.Recommendation
import com.dicoding.recraftify.data.response.RecommendationItem
import com.dicoding.recraftify.setting.ResultState
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ResultViewModel(private val repository: Repository) : ViewModel() {
    private val _recommendations = MutableLiveData<List<Recommendation>>()
    val recommendations: LiveData<List<Recommendation>> get() = _recommendations

    fun setRecommendation(recommendation: List<Recommendation>) {
        _recommendations.postValue(recommendation)
    }
}

