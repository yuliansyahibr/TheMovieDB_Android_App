package com.dicoding.tmdbapp.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val movies = liveData {
        emitSource(moviesUseCase.getTopRatedMovies().asLiveData().cachedIn(viewModelScope))
    }

    fun getList()= movies
}