package com.dicoding.tmdbapp.core.data

import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.data.source.local.LocalMoviesDataSource
import com.dicoding.tmdbapp.core.data.source.remote.RemoteMoviesDataSource
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.repository.IMoviesRepository
import com.dicoding.tmdbapp.core.util.DataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : IMoviesRepository {

    override suspend fun getMovie(id: Long): Movie? = withContext(ioDispatcher) {
        return@withContext DataMapper.movieResponseToMovie(remoteMoviesDataSource.getMovie(id))
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> = remoteMoviesDataSource.getPopularMovies()

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> = withContext(ioDispatcher) {
        return@withContext remoteMoviesDataSource.getTopRatedMovies()
    }

    override suspend fun getFavouriteMovie(id: Long): Movie? = withContext(ioDispatcher) {
        return@withContext DataMapper.movieEntityToMovie(localMoviesDataSource.getMovie(id))
    }

    override suspend fun getFavouriteMovies(): Flow<List<Movie>> = withContext(ioDispatcher) {
        return@withContext localMoviesDataSource.getMovies().map { list ->
            list.map {
                DataMapper.movieEntityToMovie(it)!!
            }
        }
    }

    override suspend fun addFavouriteMovie(movie: Movie) = withContext(ioDispatcher) {
        val movieEntity = DataMapper.movieToMovieEntity(movie)
        return@withContext localMoviesDataSource.addFavouriteMovie(movieEntity)
    }

    override suspend fun deleteFavouriteMovie(movie: Movie) = withContext(ioDispatcher) {
        val movieEntity = DataMapper.movieToMovieEntity(movie)
        return@withContext localMoviesDataSource.deleteFavouriteMovie(movieEntity)
    }
}