package com.jffp.letsharemovies.repositories

import com.jffp.letsharemovies.services.MovieApiService
import retrofit2.Retrofit

class MovieRepo(private val movieApiService: MovieApiService) {

    suspend fun getAllMovies() = movieApiService.getMovieList(1)

}