package com.jffp.letsharemovies.repositories

import androidx.annotation.WorkerThread
import com.jffp.letsharemovies.daos.MovieDao
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.database.LocalInjector
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.services.MovieApiService
import kotlinx.coroutines.flow.Flow

class MovieRepo(private val movieApiService: MovieApiService,
                val appDatabase: AppDatabase? = LocalInjector.injectDb()) {

    //From network
    suspend fun getPopularMovies() = movieApiService.getPopularMovieList(1)

    suspend fun getTopRatedMovies() = movieApiService.getTopRatedMovieList(1)

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun allMovies(movieDao: MovieDao): Flow<List<Movie>> = movieDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: Movie) {
        appDatabase?.movieDao()?.insertAll(movie)
    }

}