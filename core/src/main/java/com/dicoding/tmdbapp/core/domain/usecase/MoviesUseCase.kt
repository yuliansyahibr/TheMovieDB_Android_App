package com.dicoding.tmdbapp.core.domain.usecase

import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    suspend fun getMovie(id: Long): Movie?
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun getFavouriteMovie(id: Long): Movie?
    suspend fun getFavouriteMovies(): Flow<List<Movie>>
    suspend fun addFavouriteMovie(movie: Movie)
    suspend fun deleteFavouriteMovie(movie: Movie)
}