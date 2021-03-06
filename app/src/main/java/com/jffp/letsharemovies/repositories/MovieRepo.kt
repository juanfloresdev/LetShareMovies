package com.jffp.letsharemovies.repositories

import androidx.annotation.WorkerThread
import com.jffp.letsharemovies.daos.MovieDao
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.database.DatabaseInjector
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.services.MovieApiClientInjector
import com.jffp.letsharemovies.services.MovieApiService
import kotlinx.coroutines.flow.Flow

class MovieRepo(private val movieApiService: MovieApiService? = MovieApiClientInjector.injectDoggoApiService(),
                private val appDatabase: AppDatabase? = DatabaseInjector.injectDb()) {

    //From network
    suspend fun getPopularMovies(page: Int) = movieApiService?.getPopularMovieList(page)

    suspend fun getTopRatedMovies(page: Int) = movieApiService?.getTopRatedMovieList(page)

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun allMovies(): Flow<List<Movie>>? = appDatabase?.movieDao()?.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(movies: List<Movie>) {
        appDatabase?.movieDao()?.deleteAndInsert(movies = movies.map { it }.toTypedArray())
    }

}