package com.dicoding.recraftify.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.setting.di.Injection
import com.dicoding.recraftify.ui.activity.signup.SignupViewModel
import com.dicoding.recraftify.ui.activity.welcome.WelcomeViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unkown ViewModel class: "+modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory{
            return ViewModelFactory(Injection.provideInjection(context))
        }
    }
}