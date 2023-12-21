package com.dicoding.recraftify.ui.activity.result


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.Recommendation

class ResultViewModel(private val repository: Repository) : ViewModel() {
    private val _recommendations = MutableLiveData<List<Recommendation>>()
    val recommendations: LiveData<List<Recommendation>> get() = _recommendations

    fun setRecommendation(recommendation: List<Recommendation>) {
        _recommendations.postValue(recommendation)
    }
}

