package com.jffp.letsharemovies.ui.main.mainfragments.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jffp.letsharemovies.adapters.MovieAdapter
import com.jffp.letsharemovies.databinding.FragentMoviesBinding
import com.jffp.letsharemovies.enums.ECustonNav
import com.jffp.letsharemovies.repositories.MovieRepo
import com.jffp.letsharemovies.services.MovieApiClient
import com.jffp.letsharemovies.services.MovieApiService
import com.jffp.letsharemovies.ui.main.mainfragments.ActionFragment

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
        val apiService = MovieApiClient.getIntance().create(MovieApiService::class.java)
        val mainRepository = MovieRepo(apiService)

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(mainRepository)).get(
            MoviesViewModel::class.java
        )


        viewModel.movieList.observe(viewLifecycleOwner) {
            Log.i("response", it.toString())
            _adapter.submitList(it)
        }

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

        viewModel.getMovies(_eCustomNavAction.catalogType)


    }

    companion object{
        fun newInstance() = MoviesFragment()
    }



}