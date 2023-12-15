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
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.uriToFile
import com.dicoding.recraftify.ui.activity.CameraActivity
import com.dicoding.recraftify.ui.activity.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.recraftify.ui.activity.result.ResultActivity
import com.dicoding.recraftify.R
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.setting.reduceFileImage

private const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"
@RequiresApi(Build.VERSION_CODES.Q)
class ScanFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentScanBinding
    private var currentImageUri: Uri? = null
    private lateinit var viewModel: ScanViewModel

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
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[ScanViewModel::class.java]
        binding = FragmentScanBinding.inflate(inflater, container, false)

        binding.previewImageView.setOnClickListener { startGaleri() }
        binding.galleryButton.setOnClickListener { startGaleri() }
        binding.cameraButton.setOnClickListener { startCameraX() }
        binding.uploadButton.setOnClickListener {
            uploadScan()
        }
        return binding.root
    }


    private  fun startGaleri(){
        launcherGalery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private val launcherGalery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->
        if (uri != null){
            currentImageUri = uri
            showImage()
        }else{
            Log.d("Photo Picker", "No media selected")
        }
    }
    private fun startCameraX(){
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherCameraX.launch(intent)

    }
    private val launcherCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERAX_RESULT){
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }
    private fun showImage(){
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
    private fun uploadScan(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            viewModel.uploadWaste(imageFile).observe(viewLifecycleOwner){waste ->
                if(waste != null) {
                    when (waste) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            showToast(waste.data.message)
                            showLoading(false)
                            val intent = Intent(requireContext(), ResultActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, waste.data.data.result)
                            startActivityForResult(intent, REQUEST_CODE_SCAN)
                        }

                        is ResultState.Error -> {
                            showToast(waste.error.toString())
                            showLoading(false)
                        }

                    }
                }
            }
        } ?: showToast(getString(R.string.warning_empty))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK){

        }
    }

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
    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        private const val REQUEST_CODE_SCAN = 123
    }
}