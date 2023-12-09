package com.dicoding.recraftify.data.api

import com.dicoding.recraftify.data.response.DataItem
import com.dicoding.recraftify.data.response.LoginResponse
import com.dicoding.recraftify.data.response.SignupResponse
import com.dicoding.recraftify.data.response.WasteResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/waste/search")
    suspend fun seacrhRecipe(
        @Query("query") query: String
    ): List<DataItem>
    @GET("/waste")
    suspend fun recipe():WasteResponse
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
    @FormUrlEncoded
    @POST("/signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse
}