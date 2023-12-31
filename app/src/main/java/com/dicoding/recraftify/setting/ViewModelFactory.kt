package com.dicoding.recraftify.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.recraftify.data.Repository
import com.dicoding.recraftify.setting.di.Injection
import com.dicoding.recraftify.ui.activity.detailhistory.DetailHistoryViewModel
import com.dicoding.recraftify.ui.activity.detailresep.DetailViewModel
import com.dicoding.recraftify.ui.activity.login.LoginViewModel
import com.dicoding.recraftify.ui.activity.result.ResultViewModel
import com.dicoding.recraftify.ui.activity.signup.SignupViewModel
import com.dicoding.recraftify.ui.fragment.history.HistoryViewModel
import com.dicoding.recraftify.ui.fragment.home.HomeViewModel
import com.dicoding.recraftify.ui.fragment.profile.ProfileViewModel
import com.dicoding.recraftify.ui.fragment.scan.ScanViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailHistoryViewModel::class.java) -> {
                DetailHistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
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