package com.jffp.letsharemovies.ui.main.mainfragments.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jffp.letsharemovies.daos.MovieDao
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.enums.ECatalogType

import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

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
                if (response != null) {
                    if (response.isSuccessful) {
                        movieList.postValue(response.body()?.results)

                        //Save data
                        response.body()?.results?.let { deleteAndInsert(it) }

                        loading.value = false
                    } else {
                        loadFromDB()
                        onError("Error : ${response.message()} ")
                    }
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

    fun deleteAndInsert(movies: List<Movie>) = viewModelScope.launch {
        movieRepo.insertAll(movies)
    }

    fun fetchDatabaseMovies(): Flow<List<Movie>>? = movieRepo.allMovies()

    fun loadFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                fetchDatabaseMovies()?.collect() {
                    movieList.postValue(it)
                }
            }
        }
    }


}