package com.dicoding.tmdbapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("runtime")
    val runtime: String,
    @SerializedName("genres")
    val genres: ArrayList<GenreResponse>,
    @SerializedName("poster_path")
    val poster: String? = "",
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("vote_count")
    var userCount: Int,
    @SerializedName("vote_average")
    var userScore: Double,
)