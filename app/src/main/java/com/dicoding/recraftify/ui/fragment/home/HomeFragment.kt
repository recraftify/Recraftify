package com.dicoding.recraftify.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.recraftify.R
import com.dicoding.recraftify.data.response.DataItem
import com.dicoding.recraftify.databinding.FragmentHomeBinding
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.ui.activity.login.LoginActivity
import com.dicoding.recraftify.ui.fragment.scan.ARG_PARAM2

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment(), View.OnCreateContextMenuListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var binding: FragmentHomeBinding

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
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession().observe(requireActivity()){user ->
            if(!user.isLogin){
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                activity?.finish()
            }
        }
        adapter = HomeAdapter()
        binding.rvResep.adapter = adapter
        binding.rvResep.layoutManager = LinearLayoutManager(context)
        setAction()

    }


    private fun setAction(){
        viewModel.getRecipe().observe(viewLifecycleOwner){result ->
            when(result){
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    if (result.data.data.isEmpty()){
                        Toast.makeText(requireContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }else{
                        adapter.submitList(result.data.data)
                        Toast.makeText(requireContext(), "Data berhasil dimuat", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout){
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Logout!")
                setMessage("Apakah anda yakin ingin logout?")
                setPositiveButton("Logout") { _, _ ->
                    viewModel.logout()
                }
                create()
                show()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        @JvmStatic
        fun getInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}