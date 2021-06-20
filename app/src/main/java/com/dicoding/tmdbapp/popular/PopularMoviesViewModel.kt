package com.dicoding.tmdbapp.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    moviesUseCase: MoviesUseCase
) : ViewModel() {

    val movies: LiveData<PagingData<Movie>> =
        moviesUseCase.getPopularMovies().cachedIn(viewModelScope).asLiveData()
}