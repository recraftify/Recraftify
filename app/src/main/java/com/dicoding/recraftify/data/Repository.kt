package com.dicoding.recraftify.data

import androidx.lifecycle.liveData
import com.dicoding.recraftify.data.api.ApiService
import com.dicoding.recraftify.data.preferences.UserModel
import com.dicoding.recraftify.data.preferences.UserPreference
import com.dicoding.recraftify.data.response.LoginResponse
import com.dicoding.recraftify.data.response.ScanResponse
import com.dicoding.recraftify.data.response.SignupResponse
import com.dicoding.recraftify.data.response.WasteResponse
import com.dicoding.recraftify.setting.ResultState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class Repository constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){
    fun upWasteScan(file:File) = liveData {
        emit(ResultState.Loading)
        val requestBody = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestBody
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody)
            emit(ResultState.Success(successResponse))
        }catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            if (jsonInString != null && jsonInString.isNotEmpty()){
                emit(ResultState.Error(jsonInString))
            }else{
                try {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ScanResponse::class.java)
                    emit(ResultState.Error(errorBody.message))
                }catch (jsonException: JsonSyntaxException){
                    emit(ResultState.Error("Error parsing error response"))
                }
            }
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