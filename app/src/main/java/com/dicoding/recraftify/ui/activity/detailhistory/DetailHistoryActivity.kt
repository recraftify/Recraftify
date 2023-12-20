package com.dicoding.recraftify.ui.activity.detailhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.HistoryIdResponse
import com.dicoding.recraftify.databinding.ActivityDetailHistoryBinding
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.ViewModelFactory

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailHistoryBinding
    private lateinit var viewModel: DetailHistoryViewModel
    private lateinit var recommendationAdapter: RecommendationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[DetailHistoryViewModel::class.java]
        supportActionBar?.hide()
        val history = intent.getStringExtra(dataId)
        history?.let { viewModel.getHistory(it) }
        recommendationAdapter = RecommendationAdapter()
        binding.rvRecipe.adapter = recommendationAdapter
        binding.rvRecipe.layoutManager = LinearLayoutManager(this)
        viewModel.historyDetail.observe(this){history ->
            when(history){
                is ResultState.Loading ->{

                }
                is ResultState.Success -> {
                    val data = history.data
                    setHistoryDetail(data)
                }
                is ResultState.Error -> {
                    Log.e("DetailHistoryActivity", "Error: ${history.error}")
                }

                else -> {}
            }
        }
        viewModel.recommendations.observe(this){recommendation ->
            when(recommendation){
                is ResultState.Success -> {
                    val recommendationList = recommendation.data
                    if (!recommendationList.isNullOrEmpty()){
                        recommendationAdapter.submitList(recommendationList)
                    }
                }
                is ResultState.Error -> {
                    Log.e("DetailHistoryActivity", "Error fetching recommendations: ${recommendation.error}")
                }

                else -> {}
            }
        }
    }
    private fun setHistoryDetail(dataHistory: HistoryIdResponse){
        binding.apply {
            Glide.with(this@DetailHistoryActivity)
                .load(dataHistory.data?.uploadedImage)
                .into(ivScanWaste)
            jenisSampah.text = dataHistory.data?.trashType
            Glide.with(this@DetailHistoryActivity)
                .load(dataHistory.data?.imageResult)
                .into(tongSampah)

        }
    }
    companion object{
        const val dataId = "id"
    }
}