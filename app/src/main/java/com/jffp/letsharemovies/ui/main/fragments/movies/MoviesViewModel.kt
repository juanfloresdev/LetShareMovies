package com.jffp.letsharemovies.ui.main.fragments.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jffp.letsharemovies.enums.ECatalogType

import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class MoviesViewModel(private val movieRepo: MovieRepo) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val nextPageAvialabe = MutableLiveData<Boolean>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getMovies(eCatalogType: ECatalogType, page: Int) {
//        val database = AppDatabase.getDatabase()
//        val movieDao = database.movieDao()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = when (eCatalogType) {
                ECatalogType.POPULAR -> movieRepo.getPopularMovies(page)
                else -> movieRepo.getTopRatedMovies(page)
            }

            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        if (movieList.value == null) {
                            movieList.postValue(response.body()?.results)
                        } else {
                            var listaAnterior: MutableList<Movie> =
                                (movieList.value as MutableList<Movie>?)!!
                            response.body()?.results?.let { listaAnterior.addAll(it) }
                            movieList.postValue(listaAnterior)
                        }


                        val nextPageCond = (response.body()?.page != response.body()?.totalPages)
                        nextPageAvialabe.postValue(nextPageCond)
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