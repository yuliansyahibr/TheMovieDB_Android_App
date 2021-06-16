package com.dicoding.tmdbapp.favourite.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesInteractor
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMovieDetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _movieResult: MutableLiveData<Movie> = MutableLiveData()
    private var _movie: Movie? = null

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _isFavourite = MutableLiveData(false)
    val isFavourite: LiveData<Boolean> = _isFavourite

    private val _addedToFavourites = MutableLiveData(false)
    val addedToFavourites: LiveData<Boolean> = _addedToFavourites
    private val _removedFromFavourites = MutableLiveData(false)
    val removedFromFavourites: LiveData<Boolean> = _removedFromFavourites

    fun getMovie(id: Long): MutableLiveData<Movie> {
        setLoading(true)
        setFavourite(false)
        viewModelScope.launch {
            moviesUseCase.getMovie(id).let {
                _movieResult.postValue(it)
                _movie = it
                moviesUseCase.getFavouriteMovie(id).let { favMovie ->
                    if(favMovie != null) {
                        setFavourite(true)
                    }
                }

            }
            setLoading(false)
        }
        return _movieResult
    }

    fun btnAction() {
        setLoading(true)
        viewModelScope.launch {
            _movie?.let {
                if(!it.isFavourite) {
                    _addedToFavourites.postValue(false)
                    addToFavourites(it)
                    _addedToFavourites.postValue(true)
                } else {
                    _removedFromFavourites.postValue(false)
                    removeFromFavourite(it)
                    _removedFromFavourites.postValue(true)
                }
            }
            setLoading(false)
        }
    }

    private fun setLoading(value: Boolean) {
        _loading.postValue(value)
    }

    private fun setFavourite(value: Boolean) {
        _isFavourite.postValue(value)
        _movie?.isFavourite = value
    }

    suspend fun addToFavourites(movie: Movie) {
        moviesUseCase.addFavouriteMovie(movie)
        setFavourite(true)
    }

    suspend fun removeFromFavourite(movie: Movie) {
        moviesUseCase.deleteFavouriteMovie(movie)
        setFavourite(false)
    }
}