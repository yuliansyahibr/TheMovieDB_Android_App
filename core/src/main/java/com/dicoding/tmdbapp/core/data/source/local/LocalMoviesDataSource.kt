package com.dicoding.tmdbapp.core.data.source.local

import com.dicoding.tmdbapp.core.data.source.local.entity.MovieEntity
import com.dicoding.tmdbapp.core.data.source.local.room.MoviesDatabase
import kotlinx.coroutines.flow.Flow

class LocalMoviesDataSource internal constructor(
    moviesDatabase: MoviesDatabase
) {
    private val movieDao = moviesDatabase.movieDao()

    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun getMovie(id: Long): MovieEntity? = movieDao.getDetailedMovie(id)

    suspend fun addFavouriteMovie(movieEntity: MovieEntity) = movieDao.insertFavouriteMovie(movieEntity)

    suspend fun updateFavouriteMovie(movieEntity: MovieEntity) = movieDao.updateFavoriteMovie(movieEntity)

    suspend fun deleteFavouriteMovie(movieEntity: MovieEntity) = movieDao.deleteFavouriteMovie(movieEntity)

}