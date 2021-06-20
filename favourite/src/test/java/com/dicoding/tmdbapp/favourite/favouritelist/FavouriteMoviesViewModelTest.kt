package com.dicoding.tmdbapp.favourite.favouritelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import com.dicoding.tmdbapp.core.util.TestCoroutineRule
import com.dicoding.tmdbapp.core.util.TestDataUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteMoviesViewModelTest {
    private lateinit var favouriteMoviesViewModel: FavouriteMoviesViewModel

    @Mock
    private lateinit var moviesUseCase: MoviesUseCase
    @Mock
    private lateinit var observer: Observer<List<Movie>>

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        favouriteMoviesViewModel = FavouriteMoviesViewModel(moviesUseCase)
    }

    @Test
    fun getFavouriteMovies() = testCoroutineRule.runBlockingTest {
        val mockMovies = TestDataUtil.generateMoviesDummy(10)
        val flow = flowOf(mockMovies)
        Mockito.`when`(moviesUseCase.getFavouriteMovies()).thenReturn(flow)
        favouriteMoviesViewModel.getFavouriteMovies()
        Mockito.verify(moviesUseCase).getFavouriteMovies()

        val liveData: LiveData<List<Movie>> = favouriteMoviesViewModel.list
        liveData.observeForever(observer)
        val moviesResult = liveData.value
        assertNotNull(moviesResult)
        assertEquals(mockMovies, moviesResult)

        Mockito.verify(observer).onChanged(mockMovies)

    }
}