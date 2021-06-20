package com.dicoding.tmdbapp.core.util

import com.dicoding.tmdbapp.core.data.source.local.entity.MovieEntity
import com.dicoding.tmdbapp.core.data.source.remote.response.MovieResponse
import com.dicoding.tmdbapp.core.data.source.remote.response.PopularMovieItemResponse
import com.dicoding.tmdbapp.core.domain.model.Movie

object DataMapper {

    fun popularMovieItemResponseToMovie(movieItemResponse: PopularMovieItemResponse): Movie {
        return movieItemResponse.let {
            Movie(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate ?: "Unknown",
                poster = it.posterPath ?: "",
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                userCount = it.voteCount,
                userScore = it.voteAverage
            )
        }
    }

    fun movieResponseToMovie(movieResponse: MovieResponse?): Movie? {
        if(movieResponse == null) return null
        return movieResponse.let {
            Movie(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate ?: "Unknown",
                runtime = it.runtime,
                genres = it.genres.joinToString(", "){ genre ->  genre.name },
                poster = it.poster ?: "",
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                status = it.status,
                userCount = it.userCount,
                userScore = it.userScore
            )
        }
    }

    fun movieEntityToMovie(movieEntity: MovieEntity?): Movie? {
        if(movieEntity == null) return null
        return movieEntity.let {
            Movie(
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
                isFavourite = true,
                addedAt = it.addedAt
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
                addedAt = it.addedAt ?: System.currentTimeMillis()/1000
            )
        }
    }

}