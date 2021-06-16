package com.dicoding.tmdbapp.favourite.favouritelist

import androidx.lifecycle.*
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesInteractor
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
                _list.value = it
                setLoading(false)
            }
        }
    }
}