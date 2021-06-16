package com.dicoding.tmdbapp.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val movies: MutableLiveData<PagingData<Movie>> = MutableLiveData()

    fun getTopRatedMovies(): LiveData<PagingData<Movie>> {
        viewModelScope.launch {
            moviesUseCase.getTopRatedMovies().cachedIn(viewModelScope).collect {
                movies.value = it
            }
        }
        return movies
    }
}