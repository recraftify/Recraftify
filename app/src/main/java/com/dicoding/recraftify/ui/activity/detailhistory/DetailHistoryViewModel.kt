package com.dicoding.recraftify.ui.activity.detailhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.HistoryIdResponse
import com.dicoding.recraftify.data.response.RecommendationItem
import com.dicoding.recraftify.setting.ResultState
import kotlinx.coroutines.launch

class DetailHistoryViewModel(private  var repository: Repository): ViewModel() {
    private val _historyDetail = MutableLiveData<ResultState<HistoryIdResponse>>()
    val historyDetail: LiveData<ResultState<HistoryIdResponse>> = _historyDetail

    private val _recommendations = MutableLiveData<ResultState<List<RecommendationItem>>>()
    val recommendations: LiveData<ResultState<List<RecommendationItem>>> = _recommendations

    fun getHistory(id: String){
        viewModelScope.launch {
            try {
                _historyDetail.value = ResultState.Loading
                _recommendations.value = ResultState.Loading
                val response = repository.getHistoryById(id)
                _historyDetail.value = ResultState.Success(response)
                _recommendations.value = ResultState.Success(response.data?.recommendation.orEmpty().filterNotNull())
            }catch (e:Exception){
                _historyDetail.value = ResultState.Error(e.message ?: "Unkown error")
                _recommendations.value = ResultState.Error(e.message ?: "Unkown error")
            }
        }
    }
}