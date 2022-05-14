package com.jffp.letsharemovies.ui.main.fragments.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jffp.letsharemovies.repositories.MovieRepo

class MoviesViewModelFactory constructor(private val repository: MovieRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            MoviesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}