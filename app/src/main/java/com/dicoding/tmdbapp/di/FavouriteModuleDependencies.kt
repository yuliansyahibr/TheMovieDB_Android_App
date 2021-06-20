package com.dicoding.tmdbapp.di

import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import com.dicoding.tmdbapp.core.ui.MovieRVAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavouriteModuleDependencies {

    fun moviesUseCase(): MoviesUseCase

    fun movieRVAdapter(): MovieRVAdapter

}