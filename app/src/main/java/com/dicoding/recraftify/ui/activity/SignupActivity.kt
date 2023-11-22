package com.dicoding.recraftify.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.recraftify.MainActivity
import com.dicoding.recraftify.databinding.ActivitySignUpBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnSigup.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}