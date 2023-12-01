package com.dicoding.recraftify.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.recraftify.R
import com.dicoding.recraftify.setting.ViewModelFactory
import com.dicoding.recraftify.ui.activity.login.LoginActivity
import com.dicoding.recraftify.ui.fragment.scan.ARG_PARAM2

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment(), View.OnCreateContextMenuListener {
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

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
        return inflater.inflate(R.layout.fragment_home, container, false)

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
                setPositiveButton("Lanjut") { _, _ ->
                    viewModel.logout()
                    viewModel.getSession().observe(requireActivity()){user ->
                        if(!user.isLogin){
                            startActivity(Intent(requireActivity(), LoginActivity::class.java))
                            activity?.finish()
                        }
                    }
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