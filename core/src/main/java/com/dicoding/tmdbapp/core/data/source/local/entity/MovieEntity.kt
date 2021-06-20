package com.dicoding.tmdbapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "runtime")
    val runtime: String,
    @ColumnInfo(name = "genres")
    val genres: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "language")
    val originalLanguage: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "user_count")
    val userCount: Int = 0,
    @ColumnInfo(name = "user_score")
    val userScore: Double = 0.0,
    @ColumnInfo(name="added_at")
    val addedAt: Long
)