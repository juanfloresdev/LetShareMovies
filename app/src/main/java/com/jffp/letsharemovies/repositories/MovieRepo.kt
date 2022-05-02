package com.jffp.letsharemovies.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jffp.letsharemovies.daos.MovieDao
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.database.DatabaseInjector
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.paging.MoviesPaginSource
import com.jffp.letsharemovies.services.MovieApiClientInjector
import com.jffp.letsharemovies.services.MovieApiService
import kotlinx.coroutines.flow.Flow

class MovieRepo(private val movieApiService: MovieApiService = MovieApiClientInjector.injectApiService(),
                private val appDatabase: AppDatabase? = DatabaseInjector.injectDb()) {

    //From network
    suspend fun getPopularMovies() = movieApiService?.getPopularMovieList(1)

    suspend fun getTopRatedMovies() = movieApiService?.getTopRatedMovieList(1)

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


    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        //get doggo repository instance
        fun getInstance() = MovieRepo()
    }

    //for live data users
    fun letPagginMoviesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviesPaginSource(movieApiService) }
        ).flow
    }


    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }


}