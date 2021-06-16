package com.dicoding.tmdbapp.core.domain.usecase

import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : MoviesUseCase {

    override suspend fun getMovie(id: Long): Movie? = moviesRepository.getMovie(id)

    override fun getPopularMovies(): Flow<PagingData<Movie>> = moviesRepository.getPopularMovies()

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> = moviesRepository.getTopRatedMovies()

    override suspend fun getFavouriteMovie(id: Long): Movie? = moviesRepository.getFavouriteMovie(id)

    override suspend fun getFavouriteMovies(): Flow<List<Movie>> = moviesRepository.getFavouriteMovies()

    override suspend fun addFavouriteMovie(movie: Movie)  = moviesRepository.addFavouriteMovie(movie)

    override suspend fun deleteFavouriteMovie(movie: Movie) = moviesRepository.deleteFavouriteMovie(movie)
}