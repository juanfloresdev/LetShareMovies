package com.jffp.letsharemovies.ui.main.mainfragments.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import kotlinx.coroutines.*

class MoviesViewModel(private val movieRepo: MovieRepo) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepo.getAllMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.results)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}