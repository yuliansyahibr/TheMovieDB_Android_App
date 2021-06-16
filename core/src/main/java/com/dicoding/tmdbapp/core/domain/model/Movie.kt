package com.dicoding.tmdbapp.core.domain.model

import android.os.Parcelable
import com.dicoding.tmdbapp.core.util.Constants.BASE_POSTER_URL
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val title: String,
    val overview: String = "",
    val poster: String = "",
    val runtime: String = "",
    val genres: String = "",
    val originalLanguage: String = "",
    val status: String = "",
    val userCount: Int = 0,
    val userScore: Double = 0.0,
    val releaseDate: String = "",
    var isFavourite: Boolean = false
) : Parcelable {

    fun getPosterImageURL(): String {
        if(poster == "") {
            return poster
        }
        return BASE_POSTER_URL+poster
    }

    @IgnoredOnParcel
    val releaseYear: String
        get() = if(releaseDate != "") {
            // mm/dd/yyy format
            releaseDate.split("-").first()
        } else {
            ""
        }

}
