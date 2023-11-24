package com.dicoding.recraftify.ui.activity.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.recraftify.MainActivity
import com.dicoding.recraftify.databinding.ActivityWelcomeBinding
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.ui.activity.login.LoginActivity
import com.dicoding.recraftify.ui.activity.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getSession().observe(this){ login ->
                if (login.isLogin){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnDaftar.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}