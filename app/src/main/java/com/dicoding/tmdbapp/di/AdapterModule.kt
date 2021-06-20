package com.dicoding.tmdbapp.di

import com.dicoding.tmdbapp.core.ui.MovieRVAdapter
import com.dicoding.tmdbapp.core.ui.MoviesPagingAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    fun provideMovieRVAdapter() = MovieRVAdapter()

    @Provides
    fun provideMoviePagingAdapter() = MoviesPagingAdapter()

}