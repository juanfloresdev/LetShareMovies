package com.jffp.letsharemovies.ui.main

//import com.jffp.letsharemovies.data.MoviesRepository
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.model.ApiResponse
import com.jffp.letsharemovies.services.MovieApiService
import com.jffp.letsharemovies.services.MovieApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



}