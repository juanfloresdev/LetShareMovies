package com.jffp.letsharemovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.repositories.MovieRepo.Companion.DEFAULT_PAGE_INDEX
import com.jffp.letsharemovies.services.MovieApiService
import retrofit2.HttpException
import java.io.IOException

class MoviesPaginSource(val apiService: MovieApiService) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val lista =
                apiService.getPopularMovieList(page).body()?.results as List<Movie>

            LoadResult.Page(
                lista, prevKey = if (page == 1) null else page - 1,
                nextKey = if (lista.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}