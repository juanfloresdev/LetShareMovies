package com.jffp.letsharemovies.daos

import androidx.room.*
import com.jffp.letsharemovies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

//    @Query("SELECT * FROM movie WHERE uid IN (:movieIds)")
//    fun loadAllByIds(movieIds: IntArray): List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Insert
    suspend fun insertAll(vararg movies: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndInsert(vararg movies: Movie){
        deleteAll()
        insertAll(*movies)
    }

}