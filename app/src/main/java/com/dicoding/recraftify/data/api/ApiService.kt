package com.dicoding.recraftify.data.api

import com.dicoding.recraftify.data.response.HistoryIdResponse
import com.dicoding.recraftify.data.response.HistoryResponse
import com.dicoding.recraftify.data.response.LoginResponse
import com.dicoding.recraftify.data.response.ProfileResponse
import com.dicoding.recraftify.data.response.WasteIdResponse
import com.dicoding.recraftify.data.response.ScanResponse
import com.dicoding.recraftify.data.response.SignupResponse
import com.dicoding.recraftify.data.response.WasteResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("profile")
    suspend fun profile(): ProfileResponse
    @GET("waste/scan/history/{id}")
    suspend fun historyId(
        @Path("id") id: String
    ): HistoryIdResponse
    @GET("waste/scan/history")
    suspend fun history(): HistoryResponse
    @Multipart
    @POST("waste/scan")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): ScanResponse
    @GET("waste")
    suspend fun recipe(): WasteResponse
    @GET("waste/{id}")
    suspend fun recipeById(
        @Path("id") id: String
    ): WasteIdResponse
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
    @FormUrlEncoded
    @POST("signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse
}