package com.dicoding.recraftify.ui.activity.detailresep

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.dicoding.recraftify.R
import com.dicoding.recraftify.data.response.DataItem
import com.dicoding.recraftify.databinding.ActivityDetailBinding


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupAction(){
        if (data != null){
            val dataItem = intent.getParcelableExtra<DataItem>(data)
                binding.apply {
                    judulResep.text = dataItem?.name
                    jenisSampah.text = dataItem?.wasteType
                    metode.text = dataItem?.method
                    petunjuk.text = dataItem?.instructions
                    Glide.with(this@DetailActivity)
                        .load(dataItem?.url)
                        .into(ivRecipe)
                }
        }
    }

    companion object{
        const val data = "id"
    }
}