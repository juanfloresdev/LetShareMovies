package com.jffp.letsharemovies.ui.main.mainfragments.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jffp.letsharemovies.daos.MovieDao
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.enums.ECatalogType

import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import kotlinx.coroutines.*

class MoviesViewModel(private val movieRepo: MovieRepo) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getMovies(eCatalogType: ECatalogType) {
//        val database = AppDatabase.getDatabase()
//        val movieDao = database.movieDao()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = when (eCatalogType) {
                ECatalogType.POPULAR -> movieRepo.getPopularMovies()
                else -> movieRepo.getTopRatedMovies()
            }

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.results)

                    //response.body()?.results?.let { movieRepo.insert(movieDao, it.get(0)) }
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

//    fun insert(word: Movie) = viewModelScope.launch {
//        movieRepo.insert(word)
//    }



}