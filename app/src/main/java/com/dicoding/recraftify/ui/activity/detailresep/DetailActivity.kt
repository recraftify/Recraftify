package com.dicoding.recraftify.ui.activity.detailresep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.recraftify.databinding.ActivityDetailBinding
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.R
import com.dicoding.recraftify.data.response.WasteIdResponse
import com.dicoding.recraftify.setting.ResultState

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[DetailViewModel::class.java]

        val waste = intent.getStringExtra(data)
        waste?.let { viewModel.getRecipeById(it) }

        viewModel.recipeDetail.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    val dataItem = resultState.data
                        setWasteDetail(dataItem)
                }
                is ResultState.Error -> {
                    Log.e("DetailActivity", "Error: ${resultState.error}")
                }

                else -> {}
            }
        }
    }

    private fun setWasteDetail(dataItem: WasteIdResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(dataItem.data?.image)
                .into(ivRecipe)
            judulResep.text = dataItem.data?.name
            Glide.with(this@DetailActivity)
                .load(R.drawable.category_waste)
                .into(iconJenisSampah)
            jenisSampah.text = dataItem.data?.wasteType
            dataItem.data?.instructions?.let { instructions ->
                petunjuk.text = instructions.joinToString ( "\n" )
            }
        }
    }

    companion object{
        const val data = "id"
    }
}