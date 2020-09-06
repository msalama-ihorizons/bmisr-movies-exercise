package com.tiendito.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiendito.api.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie?)

    @Query("SELECT * from movies_table where id = :movieId ")
    fun loadMovieById(movieId: Int): LiveData<Movie?>

    @Query("SELECT * from movies_table")
    fun loadMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM movies_table where id = :id")
    suspend fun deleteMovie(id: Int?)
}