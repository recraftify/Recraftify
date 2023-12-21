package com.dicoding.recraftify.ui.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.recraftify.databinding.FragmentHistoryBinding
import com.dicoding.recraftify.setting.ResultState
import com.dicoding.recraftify.setting.ViewModelFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : HistoryViewModel
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding

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
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[HistoryViewModel::class.java]
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvHistory.layoutManager = layoutManager
        setAction()
    }

    private fun setAction(){
        viewModel.getHistory().observe(viewLifecycleOwner){history ->
            when(history){
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    if (history.data.data!!.isEmpty()){
                        Toast.makeText(requireContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }else{
                        adapter.submitList(history.data.data)
                        Toast.makeText(requireContext(), "Data berhasil dimuat", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}