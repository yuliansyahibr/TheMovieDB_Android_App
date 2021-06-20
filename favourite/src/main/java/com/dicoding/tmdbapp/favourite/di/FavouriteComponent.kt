package com.dicoding.tmdbapp.favourite.di

import com.dicoding.tmdbapp.di.FavouriteModuleDependencies
import com.dicoding.tmdbapp.favourite.favouritelist.FavouriteMoviesFragment
import dagger.Component

@Component(
    dependencies = [FavouriteModuleDependencies::class],
)
interface FavouriteComponent {

    fun inject(favouriteMoviesFragment: FavouriteMoviesFragment)

    @Component.Factory
    interface Factory {
        // Takes an instance of AppComponent when creating
        // an instance of LoginComponent
        fun create(dependencies: FavouriteModuleDependencies): FavouriteComponent
    }

}