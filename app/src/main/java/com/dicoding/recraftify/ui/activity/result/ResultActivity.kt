package com.dicoding.recraftify.ui.activity.result


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.Data
import com.dicoding.recraftify.databinding.ActivityResultBinding
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.ui.fragment.home.HomeAdapter

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var adapter: HomeAdapter
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[ResultViewModel::class.java]
        adapter = HomeAdapter()
        setupAction()
        binding.rvListRecomendation.adapter = adapter
        binding.rvListRecomendation.layoutManager = LinearLayoutManager(this)
        resultScanning()
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun resultScanning() {
        val dataItem = intent.getParcelableExtra<Data>(data)
        Log.d("Test", "$dataItem" )
        binding.apply {
            jenisSampah.text = dataItem?.result.toString()
            Glide.with(this@ResultActivity)
                .load(dataItem?.result)
                .into(ivScanWaste)
        }
    }
    private fun setupAction(){

        viewModel.getRecipe().observe(this){list ->
            when(list){
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    if (list.data.data.isEmpty()){
                        Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }else{
                        adapter.submitList(list.data.data)
                        Toast.makeText(this, "Data berhasil dimuat", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val data = "id"
    }
}