package com.dicoding.tmdbapp.di

import com.dicoding.tmdbapp.core.domain.usecase.MoviesInteractor
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideMoviesUseCase(
        moviesInteractor: MoviesInteractor
    ): MoviesUseCase

}