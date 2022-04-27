package com.jffp.letsharemovies.ui.main

//import com.jffp.letsharemovies.data.MoviesRepository
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.model.ApiResponse
import com.jffp.letsharemovies.services.MovieApiService
import com.jffp.letsharemovies.services.MovieApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val repository: MoviesRepository =  MoviesRepository.getInstance()
//        val data = repository.letMoviesFlowDb()
//        Log.e("data", data.toString())
        getMovies()
    }

    private fun getMovies() {
        val apiService = MovieApiClient.getIntance().create(MovieApiService::class.java)
        apiService.getMovieList().enqueue(object : Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                Log.e("fsda", response.toString())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}