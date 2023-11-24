package com.dicoding.recraftify.setting.di

import android.content.Context
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.data.api.ApiConfig
import com.dicoding.recraftify.data.preferences.UserPreference
import com.dicoding.recraftify.data.preferences.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideInjection(context: Context): Repository{
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return Repository(preference, apiService)
    }
}