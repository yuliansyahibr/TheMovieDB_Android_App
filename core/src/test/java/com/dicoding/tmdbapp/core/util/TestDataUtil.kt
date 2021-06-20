package com.dicoding.tmdbapp.core.util

import com.dicoding.tmdbapp.core.data.source.local.entity.MovieEntity
import com.dicoding.tmdbapp.core.data.source.remote.response.GenreResponse
import com.dicoding.tmdbapp.core.data.source.remote.response.MovieResponse
import com.dicoding.tmdbapp.core.domain.model.Movie
import kotlin.random.Random

object TestDataUtil {

    const val TIMESTAMP: Long = -1

    fun generateMoviesDummy(max: Int): List<Movie> {
        return (0..max).map {
            Movie(
                id = it.toLong(),
                title = "Dummy Movie #${Random.nextInt()}"
            )
        }
    }

    fun movieToMovieResponse(movie: Movie?): MovieResponse? {
        if(movie == null) return null
        return movie.let {
            MovieResponse(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                runtime = it.runtime,
                genres = it.genres.split(", ").mapIndexed { index, s ->
                    GenreResponse(index, s)
                } as ArrayList<GenreResponse>,
                poster = it.poster,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                status = it.status,
                userCount = it.userCount,
                userScore = it.userScore
            )
        }
    }

    fun movieToMovieEntity(movie: Movie): MovieEntity {
        return movie.let {
            MovieEntity(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                runtime = it.runtime,
                genres = it.genres,
                poster = it.poster,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                status = it.status,
                userCount = it.userCount,
                userScore = it.userScore,
                addedAt = TIMESTAMP
            )
        }
    }
}