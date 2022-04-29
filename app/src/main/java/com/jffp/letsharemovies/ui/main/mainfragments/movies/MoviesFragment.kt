package com.jffp.letsharemovies.ui.main.mainfragments.movies

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jffp.letsharemovies.adapters.MovieAdapter
import com.jffp.letsharemovies.databinding.FragentMoviesBinding
import com.jffp.letsharemovies.repositories.MovieRepo
import com.jffp.letsharemovies.services.MovieApiClientInjector
import com.jffp.letsharemovies.services.MovieApiService
import com.jffp.letsharemovies.ui.main.mainfragments.ActionFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesFragment : ActionFragment() {
    private lateinit var _binding: FragentMoviesBinding
    private lateinit var _adapter: MovieAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragentMoviesBinding.inflate(inflater, container, false)
        _binding.movieList = this
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        initHeading()
        initRecyclerView()
        initViewModel()
    }

    private fun initHeading() {
        _binding.title.text = _eCustomNavAction.catalogType.title
        _binding.subTitle.text = _eCustomNavAction.catalogType.subTitle
    }

    private fun initRecyclerView() {
        _adapter = MovieAdapter()
        _binding.recyclerViewMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _binding.recyclerViewMovies.adapter = _adapter
    }

    private fun initViewModel() {
//        val apiService = MovieApiClientInjector.getIntance().create(MovieApiService::class.java)
//        val mainRepository = MovieRepo(apiService)
        val mainRepository = MovieRepo()

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(mainRepository)).get(
            MoviesViewModel::class.java
        )

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                _binding.progressDialog.visibility = View.VISIBLE
            } else {
                _binding.progressDialog.visibility = View.GONE
            }
        }

        //TODO: Check internet connection before move along
        if (checkForInternet(requireContext())) {
            viewModel.movieList.observe(viewLifecycleOwner) {
                Log.i("response", it.toString())
                _adapter.submitList(it)
            }

            viewModel.getMovies(_eCustomNavAction.catalogType)
        } else {
            lifecycle.coroutineScope.launch {
                viewModel.fetchDatabaseMovies()?.collect() {
                    _adapter.submitList(it)
                }
            }
        }


    }

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}