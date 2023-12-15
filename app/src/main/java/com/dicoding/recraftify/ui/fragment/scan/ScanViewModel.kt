package com.dicoding.recraftify.ui.fragment.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.response.ScanResponse
import com.dicoding.recraftify.setting.ResultState
import kotlinx.coroutines.launch
import java.io.File

class ScanViewModel(private val repository: Repository): ViewModel() {

    fun uploadWaste(file: File) = repository.upWasteScan(file)

}