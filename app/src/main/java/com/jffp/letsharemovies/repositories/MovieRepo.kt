package com.jffp.letsharemovies.repositories

import com.jffp.letsharemovies.services.MovieApiService

class MovieRepo(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies() = movieApiService.getPopularMovieList(1)

    suspend fun getTopRatedMovies() = movieApiService.getTopRatedMovieList(1)


}