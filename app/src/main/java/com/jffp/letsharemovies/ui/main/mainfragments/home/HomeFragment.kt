package com.jffp.letsharemovies.ui.main.mainfragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jffp.letsharemovies.constants.HashKeys.Companion.CUSTOM_NAV_ACTION
import com.jffp.letsharemovies.constants.HashKeys.Companion.NAV_BW_FRAGMENTS
import com.jffp.letsharemovies.databinding.HomeFragmentBinding
import com.jffp.letsharemovies.enums.ECustonNav

class HomeFragment : Fragment() {
    private lateinit var _binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        _binding.homeFragment = this
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    fun redirect(to: ECustonNav) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put(CUSTOM_NAV_ACTION, to)

        val bundle = Bundle()
        bundle.putSerializable(NAV_BW_FRAGMENTS, hashMap)
        val navController = findNavController()
        navController.navigate(to.action, bundle)
    }


    companion object {
        fun newInstance() = HomeFragment()
    }


}