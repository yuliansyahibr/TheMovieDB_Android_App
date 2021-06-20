package com.dicoding.tmdbapp.toprated

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.domain.usecase.MoviesUseCase
import com.dicoding.tmdbapp.core.util.TestDataUtil
import com.dicoding.tmdbapp.core.util.TestCoroutineRule
import com.dicoding.tmdbapp.core.util.collectDataForTest
import com.dicoding.tmdbapp.core.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopRatedMoviesViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getListMovie() = testCoroutineRule.runBlockingTest{
        val mockMovies = TestDataUtil.generateMoviesDummy(15)
        val pagingData = PagingData.from(mockMovies)
        val flow = flowOf(
            pagingData
        )

        val moviesUseCase = Mockito.mock(MoviesUseCase::class.java)
        Mockito.`when`(moviesUseCase.getTopRatedMovies()).thenReturn(flow)
        val viewModel = TopRatedMoviesViewModel(moviesUseCase)

        val liveData = viewModel.getList()
        val result = liveData.getOrAwaitValue().collectDataForTest()
        Mockito.verify(moviesUseCase).getTopRatedMovies()

        assertNotNull(result)
        assertEquals(mockMovies.size, result.size)
        assertEquals(mockMovies, result)
    }
}