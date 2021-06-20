package com.dicoding.tmdbapp.favourite.favouritelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _list = MutableLiveData<List<Movie>>()
    val list: LiveData<List<Movie>>
        get() = _list

    private fun setLoading(value: Boolean) {
        _loading.postValue(value)
    }

    fun getFavouriteMovies() {
        setLoading(true)
        viewModelScope.launch {
            moviesUseCase.getFavouriteMovies().collect {
                setLoading(false)
                _list.value = it
            }
        }
    }
}