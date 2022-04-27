package com.jffp.letsharemovies.services

import retrofit2.Call
import com.jffp.letsharemovies.model.ApiResponse
import retrofit2.http.GET

interface MovieApiService {

    @GET("movie/popular?api_key=892518e5467262c36673f3ea2f3ffd0b&language=en-US&page=1")
    fun getMovieList(): Call<ApiResponse>
}