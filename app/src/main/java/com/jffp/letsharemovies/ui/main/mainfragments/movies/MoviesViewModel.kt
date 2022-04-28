package com.jffp.letsharemovies.ui.main.mainfragments.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jffp.letsharemovies.enums.ECatalogType
import com.jffp.letsharemovies.enums.ECustonNav

import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import kotlinx.coroutines.*

class MoviesViewModel(private val movieRepo: MovieRepo) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getMovies(eCatalogType: ECatalogType) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = when (eCatalogType) {
                ECatalogType.POPULAR -> movieRepo.getPopularMovies()
                else -> movieRepo.getTopRatedMovies()
            }
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