package com.dicoding.tmdbapp.core.di

import com.dicoding.tmdbapp.core.data.MoviesRepository
import com.dicoding.tmdbapp.core.domain.repository.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DataSourceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(
        moviesRepository: MoviesRepository
    ): IMoviesRepository
}