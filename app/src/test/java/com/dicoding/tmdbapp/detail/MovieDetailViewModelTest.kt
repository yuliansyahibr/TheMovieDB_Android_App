package com.dicoding.tmdbapp.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import com.dicoding.tmdbapp.core.util.TestDataUtil
import com.dicoding.tmdbapp.core.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovies = TestDataUtil.generateMoviesDummy(3)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesUseCase: MoviesUseCase

    @Mock
    private lateinit var observer: Observer<Movie>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(moviesUseCase)
    }

    @Test
    fun `get detail movie`() = testCoroutineRule.runBlockingTest {
        val dummyMovie = dummyMovies[0]

        Mockito.`when`(moviesUseCase.getMovie(dummyMovie.id)).thenReturn(dummyMovie)
        val result = viewModel.getMovie(dummyMovie.id).value
        Mockito.verify(moviesUseCase).getMovie(dummyMovie.id)
        assertNotNull(result)
        assertEquals(dummyMovie.title, result?.title)

        viewModel.getMovie(dummyMovie.id).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun `add movie to favourite list`() = testCoroutineRule.runBlockingTest {
        val dummyMovie = dummyMovies[1]
        Mockito.`when`(moviesUseCase.addFavouriteMovie(dummyMovie)).thenReturn(any())
        viewModel. addToFavourites(dummyMovie)
        Mockito.verify(moviesUseCase).addFavouriteMovie(dummyMovie)
    }

    @Test
    fun `remove movie to favourite list`() = testCoroutineRule.runBlockingTest {
        val dummyMovie = dummyMovies[2]
        Mockito.`when`(moviesUseCase.deleteFavouriteMovie(dummyMovie)).thenReturn(any())
        viewModel.removeFromFavourite(dummyMovie)
        Mockito.verify(moviesUseCase).deleteFavouriteMovie(dummyMovie)
    }
}