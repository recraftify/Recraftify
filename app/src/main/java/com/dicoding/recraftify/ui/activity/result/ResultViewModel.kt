package com.dicoding.recraftify.ui.activity.result


import androidx.lifecycle.ViewModel
import com.dicoding.recraftify.data.Repository
import java.io.File

class ResultViewModel(private val repository: Repository) : ViewModel() {
    fun getRecipe() = repository.getRecipe()
}