package com.dicoding.recraftify.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.recraftify.databinding.ActivityWelcomeBinding
import com.dicoding.recraftify.ui.activity.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
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