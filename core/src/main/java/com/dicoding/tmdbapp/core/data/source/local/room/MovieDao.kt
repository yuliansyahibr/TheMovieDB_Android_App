package com.dicoding.tmdbapp.core.data.source.local.room

import androidx.room.*
import com.dicoding.tmdbapp.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getDetailedMovie(id: Long): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteMovie(movie: MovieEntity)

    @Update
    suspend fun updateFavoriteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteFavouriteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteById(id: Long): Int

}