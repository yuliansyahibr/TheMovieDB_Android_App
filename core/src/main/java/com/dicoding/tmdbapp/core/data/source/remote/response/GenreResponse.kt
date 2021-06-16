package com.dicoding.tmdbapp.core.data.source.remote.response

data class GenreResponse(
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
