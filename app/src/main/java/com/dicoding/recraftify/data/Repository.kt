package com.dicoding.recraftify.data

import com.dicoding.recraftify.data.api.ApiService
import com.dicoding.recraftify.data.preferences.UserPreference

class Repository constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    fun register(name: String, email: String, password: String){

    }
}