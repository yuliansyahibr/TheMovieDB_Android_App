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
    var releaseDate: String = "",
    @ColumnInfo(name = "runtime")
    var runtime: String,
    @ColumnInfo(name = "genres")
    var genres: String,
    @ColumnInfo(name = "poster")
    var poster: String,
    @ColumnInfo(name = "language")
    var originalLanguage: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "user_count")
    var userCount: Int = 0,
    @ColumnInfo(name = "user_score")
    var userScore: Double = 0.0
)