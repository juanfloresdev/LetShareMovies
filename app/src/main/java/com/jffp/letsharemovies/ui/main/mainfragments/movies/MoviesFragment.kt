package com.jffp.letsharemovies.ui.main.mainfragments.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.databinding.MoviesFragmentBinding
import com.jffp.letsharemovies.repositories.MovieRepo
import com.jffp.letsharemovies.services.MovieApiClient
import com.jffp.letsharemovies.services.MovieApiService
import com.jffp.letsharemovies.ui.main.mainfragments.ActionFragment

class MoviesFragment : ActionFragment() {
    private lateinit var _binding: MoviesFragmentBinding

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        _binding.movieList = this
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)


        val apiService = MovieApiClient.getIntance().create(MovieApiService::class.java)
        val mainRepository = MovieRepo(apiService)
        //binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(mainRepository)).get(
            MoviesViewModel::class.java
        )


        viewModel.movieList.observe(viewLifecycleOwner) {
            Log.i("response", it.toString())
            _binding.listaPeliculas.text = it.toString()
//            adapter.setMovies(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                _binding.progressDialog.visibility = View.VISIBLE
            } else {
                _binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllMovies()

        _binding.title.text = _eCustomNavAction.catalogType.title
        _binding.subTitle.text = _eCustomNavAction.catalogType.subTitle
    }


}