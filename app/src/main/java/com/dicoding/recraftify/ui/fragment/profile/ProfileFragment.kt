package com.dicoding.recraftify.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.recraftify.R
import com.dicoding.recraftify.data.response.ProfileResponse
import com.dicoding.recraftify.databinding.FragmentProfileBinding
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.ViewModelFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[ProfileViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profileState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    val profileData = result.data
                    updateUI(profileData)
                }
                is ResultState.Error -> {
                    val errorMessage = result.error
                    errorMessage?.let { showToast(it) }
                }
                else -> Unit
            }
        })

        viewModel.getProfile()
        binding.logout.setOnClickListener {
            viewModel.logout()
        }
    }
    private fun updateUI(item: ProfileResponse){
        binding.apply {
            tvItemName.text = item.data?.name
            tvItemEmail.text = item.data?.email
            tvItemCreated.text = formatDate(item.data?.createdAt)
            tvItemUpdate.text = formatDate(item.data?.updatedAt)
            item.data?.let { imageUrl ->
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.profile_image)
                    .into(itemPhoto)
            }
        }
    }

    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}