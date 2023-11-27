package com.dicoding.recraftify.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.dicoding.recraftify.data.api.ApiService
import com.dicoding.recraftify.data.preferences.UserModel
import com.dicoding.recraftify.data.preferences.UserPreference
import com.dicoding.recraftify.data.response.ErrorResponse
import com.dicoding.recraftify.data.response.RegisterResponse
import com.dicoding.recraftify.setting.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){
    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }
    fun register(name: String, email: String, password: String) = liveData{
        emit(ResultState.Loading)
        try {
            val succesResponse = apiService.register(name, email, password)
            emit(ResultState.Success(succesResponse))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserModel>{
        return userPreference.getSession()
    }
    suspend fun logout(){
        userPreference.logout()
    }
}