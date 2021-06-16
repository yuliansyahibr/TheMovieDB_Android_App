package com.dicoding.tmdbapp.core.data.source.remote.network

import com.dicoding.tmdbapp.core.data.source.remote.response.MovieResponse
import com.dicoding.tmdbapp.core.data.source.remote.response.PopularMoviesResponse
import com.dicoding.tmdbapp.core.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page")
        page: Int,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): Response<PopularMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page")
        page: Int,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): Response<PopularMoviesResponse>


    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>
}