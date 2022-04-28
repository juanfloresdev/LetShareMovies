package com.jffp.letsharemovies.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jffp.letsharemovies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE uid IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Insert
    fun insertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteAll(movie: Movie)

}