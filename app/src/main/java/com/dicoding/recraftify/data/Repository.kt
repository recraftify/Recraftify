package com.dicoding.recraftify.data

import androidx.lifecycle.liveData
import com.dicoding.recraftify.data.api.ApiService
import com.dicoding.recraftify.data.preferences.UserModel
import com.dicoding.recraftify.data.preferences.UserPreference
import com.dicoding.recraftify.data.response.DataItem
import com.dicoding.recraftify.data.response.LoginResponse
import com.dicoding.recraftify.data.response.SignupResponse
import com.dicoding.recraftify.data.response.WasteResponse
import com.dicoding.recraftify.setting.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class Repository constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){
    suspend fun searchRecipe(query:String): ResultState<List<DataItem>>{
        try {
            val seacrhresult = apiService.seacrhRecipe(query)
            return ResultState.Success(seacrhresult)
        }catch (e: Exception){
            return ResultState.Error("Data tidak ditemukan")
        }
    }
    fun getRecipe() = liveData{
        emit(ResultState.Loading)
        try {
            val succesResponse = apiService.recipe()
            emit(ResultState.Success(succesResponse))
        }catch (e: Exception){
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, WasteResponse::class.java)
                emit(ResultState.Error(errorResponse.message))
            }else{
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }
    fun login(email: String, password: String) = liveData{
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        }catch (e: Exception){
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                emit(ResultState.Error(errorResponse.message))
            }else{
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }
    fun register(name: String, email: String, password: String) = liveData{
        emit(ResultState.Loading)
        try {
            val succesResponse = apiService.register(name, email, password)
            emit(ResultState.Success(succesResponse))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SignupResponse::class.java)
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