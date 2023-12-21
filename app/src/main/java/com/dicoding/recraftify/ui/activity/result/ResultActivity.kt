package com.dicoding.recraftify.ui.activity.result


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.Recommendation
import com.dicoding.recraftify.databinding.ActivityResultBinding
import com.dicoding.recraftify.setting.ViewModelFactory

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var adapter: ResultAdapter
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[ResultViewModel::class.java]

        adapter = ResultAdapter()
        binding.rvRecommendation.adapter = adapter
        binding.rvRecommendation.layoutManager = LinearLayoutManager(this)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        val result = intent.getStringExtra(EXTRA_RESULT)
        val imageTong = intent.getStringExtra(EXTRA_IMAGE_TONG_SAMPAH)
        val recommendation = intent.getParcelableArrayListExtra<Recommendation>(EXTRA_RECOMMENDATIONS)
        binding.apply {
            jenisSampah.text = result
            Glide.with(this@ResultActivity)
                .load(imageUri)
                .into(ivScanWaste)
            Glide.with(this@ResultActivity)
                .load(imageTong)
                .into(tongSampah)
        }
        viewModel.setRecommendation(recommendation.orEmpty())
        viewModel.recommendations.observe(this) { recommendations ->
            adapter.submitList(recommendations)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_IMAGE_TONG_SAMPAH ="extra_image_tong_sampah"
        const val EXTRA_RECOMMENDATIONS = "extra_recommendations"
        const val data = "id"
    }
}