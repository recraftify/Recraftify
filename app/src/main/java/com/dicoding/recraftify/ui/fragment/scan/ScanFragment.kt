package com.dicoding.recraftify.ui.fragment.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dicoding.recraftify.databinding.FragmentScanBinding
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri
import com.dicoding.recraftify.ui.activity.CameraActivity
import com.dicoding.recraftify.ui.activity.CameraActivity.Companion.CAMERAX_RESULT

private const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

class ScanFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentScanBinding
    private var currentImageUri: Uri? = null

    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), REQUIRED_PERMISSION ) == PackageManager.PERMISSION_GRANTED

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if (isGranted){
            Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
        }
    }
    private fun startCameraX(){
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherCameraX.launch(intent)

    }
    private  fun startGaleri(){
        launcherGalery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERAX_RESULT){
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private val launcherGalery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){
        uri: Uri? ->
        if (uri != null){
            currentImageUri = uri
            showImage()
        }else{
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage(){
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if(!allPermissionGranted()){
            requestPermission.launch(REQUIRED_PERMISSION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(layoutInflater)
        binding.previewImageView.setOnClickListener { startGaleri() }
        binding.galleryButton.setOnClickListener { startGaleri() }
        binding.cameraButton.setOnClickListener {
            startCameraX()
        }
        binding.uploadButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur belum tersedia", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun getInstance(param1: String, param2: String) =
            ScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}