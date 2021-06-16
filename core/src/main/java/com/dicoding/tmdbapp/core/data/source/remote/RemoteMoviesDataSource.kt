package com.dicoding.tmdbapp.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.data.source.remote.network.TMDBService
import com.dicoding.tmdbapp.core.data.source.remote.response.MovieResponse
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.util.API
import com.dicoding.tmdbapp.core.util.Constants
import kotlinx.coroutines.flow.Flow

class RemoteMoviesDataSource internal constructor(
    private val api: TMDBService
) {

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource {
                    api.getPopularMovies(it)
                }
            }
        ).flow
    }

    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource {
                    api.getTopRatedMovies(it)
                }
            }
        ).flow
    }

    suspend fun getMovie(id: Long): MovieResponse {
        return API.apiHandler(api.getMovie(id))
    }

}