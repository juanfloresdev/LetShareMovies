package com.jffp.letsharemovies.services

import retrofit2.Call
import com.jffp.letsharemovies.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular?api_key=892518e5467262c36673f3ea2f3ffd0b&language=en-US")
    suspend fun getMovieList(@Query("page") page: Int): Response<ApiResponse>
}