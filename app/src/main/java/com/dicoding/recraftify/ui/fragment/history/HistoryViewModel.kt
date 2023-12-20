package com.dicoding.recraftify.ui.fragment.history

import androidx.lifecycle.ViewModel
import com.dicoding.recraftify.data.Repository

class HistoryViewModel(private val repository: Repository): ViewModel() {
    fun getHistory() = repository.getHistory()
}