package com.jffp.letsharemovies.ui.main.mainfragments.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jffp.letsharemovies.model.ApiResponse
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo
import com.jffp.letsharemovies.services.MovieApiClient
import com.jffp.letsharemovies.services.MovieApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel(private val movieRepo: MovieRepo) : ViewModel() {
    // TODO: Implement the ViewModel
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


//
//
//    fun getMovies() {
//        val apiService = MovieApiClient.getIntance().create(MovieApiService::class.java)
//        apiService.getMovieList(1).enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                val apiResponse: ApiResponse? = response.body()
//
//                if (apiResponse != null) {
//                    val resultados: ArrayList<Movie> = apiResponse.results
//                    Log.d("Server_response", resultados.toString())
//                }
//            }
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                Log.e("Error", t.message.toString())
//            }
//        })
//    }

}