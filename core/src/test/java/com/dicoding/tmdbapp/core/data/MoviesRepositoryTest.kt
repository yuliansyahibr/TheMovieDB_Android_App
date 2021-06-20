package com.dicoding.tmdbapp.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.dicoding.tmdbapp.core.data.source.local.LocalMoviesDataSource
import com.dicoding.tmdbapp.core.data.source.remote.RemoteMoviesDataSource
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.util.TestDataUtil
import com.dicoding.tmdbapp.core.util.TestDataUtil.movieToMovieResponse
import com.dicoding.tmdbapp.core.util.TestCoroutineRule
import com.dicoding.tmdbapp.core.util.collectDataForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
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
class MoviesRepositoryTest {

    // movies with random title
    private val favouriteMoviesDummy = (TestDataUtil.generateMoviesDummy(4)).map {
        it.apply {
            isFavourite = true
            addedAt = TestDataUtil.TIMESTAMP
        }
    }
    private val popularMoviesDummy = TestDataUtil.generateMoviesDummy(10)
    private val topRatedMoviesDummy = TestDataUtil.generateMoviesDummy(10)

    @Mock
    private lateinit var moviesRemoteDataSource: RemoteMoviesDataSource
    @Mock
    private lateinit var moviesLocalDataSource: LocalMoviesDataSource

    private lateinit var moviesRepository: MoviesRepository

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        moviesRepository = MoviesRepository(
            moviesRemoteDataSource, moviesLocalDataSource, TestCoroutineDispatcher()
        )
    }

    @Test
    fun `getPopularMovies returns empty`() = testCoroutineRule.runBlockingTest {
        val emptyPagingData = PagingData.empty<Movie>()
        val flow = flowOf(
            emptyPagingData
        )
        // : Flow<PagingData<Your Data type>>
        Mockito.`when`(moviesRemoteDataSource.getPopularMovies()).thenReturn(flow)
        val tmp = moviesRepository.getPopularMovies().take(1).toList().first()
        Mockito.verify(moviesRemoteDataSource).getPopularMovies()
        // result: List<Your Data type>
        val result = tmp.collectDataForTest()

        assertEquals(0, result.size)
        assertEquals(listOf<Movie>(), result)
    }
    @Test
    fun `getTopRatedMovies returns empty`() = testCoroutineRule.runBlockingTest {
        val emptyPagingData = PagingData.empty<Movie>()
        val flow = flowOf(
            emptyPagingData
        )
        // : Flow<PagingData<Your Data type>>
        Mockito.`when`(moviesRemoteDataSource.getTopRatedMovies()).thenReturn(flow)
        val tmp = moviesRepository.getTopRatedMovies().take(1).toList().first()
        Mockito.verify(moviesRemoteDataSource).getTopRatedMovies()
        // result: List<Your Data type>
        val result = tmp.collectDataForTest()

        assertEquals(0, result.size)
        assertEquals(listOf<Movie>(), result)
    }

    @Test
    fun `get paged popular list`() = testCoroutineRule.runBlockingTest {
        val pagingData = PagingData.from(popularMoviesDummy)
        val flow = flowOf(
            pagingData
        )

        Mockito.`when`(moviesRemoteDataSource.getPopularMovies()).thenReturn(flow)
        val tmp = moviesRepository.getPopularMovies().take(1).toList().first()
        Mockito.verify(moviesRemoteDataSource).getPopularMovies()
        val result = tmp.collectDataForTest()

        assertEquals(popularMoviesDummy.size, result.size)
        assertEquals(popularMoviesDummy, result)
    }
    @Test
    fun `get top rated list`() = testCoroutineRule.runBlockingTest {
        val pagingData = PagingData.from(topRatedMoviesDummy)
        val flow = flowOf(
            pagingData
        )

        Mockito.`when`(moviesRemoteDataSource.getTopRatedMovies()).thenReturn(flow)
        val tmp = moviesRepository.getTopRatedMovies().take(1).toList().first()
        Mockito.verify(moviesRemoteDataSource).getTopRatedMovies()
        val result = tmp.collectDataForTest()

        assertEquals(topRatedMoviesDummy.size, result.size)
        assertEquals(topRatedMoviesDummy, result)
    }
    @Test
    fun `get movie detail remote`() = testCoroutineRule.runBlockingTest {
        val movieDummy = popularMoviesDummy[0]
        val movieResponseDummy = movieToMovieResponse(movieDummy)
        val movieId = movieDummy.id
        Mockito.`when`(moviesRemoteDataSource.getMovie(movieId)).thenReturn(movieResponseDummy)
        val movie = moviesRepository.getMovie(movieId)
        Mockito.verify(moviesRemoteDataSource).getMovie(movieId)
        assertEquals(movie, movieDummy)
    }

    @Test
    fun `get favourite movie list`() = testCoroutineRule.runBlockingTest {
        val movieEntities = favouriteMoviesDummy.map {
            TestDataUtil.movieToMovieEntity(it)
        }
        val dummyFlow = flowOf(
            movieEntities
        )
        Mockito.`when`(moviesLocalDataSource.getMovies()).thenReturn(dummyFlow)
        var movies: List<Movie> = listOf()
        moviesRepository.getFavouriteMovies().collectLatest {
            movies = it
        }
        Mockito.verify(moviesLocalDataSource).getMovies()
        assertEquals(movies.size, favouriteMoviesDummy.size)
        assertEquals(movies, favouriteMoviesDummy)
    }

    @Test
    fun `get favourite movie detail`() = testCoroutineRule.runBlockingTest {
        val dummyMovie = favouriteMoviesDummy[0]
        val movieId = dummyMovie.id
        Mockito.`when`(moviesLocalDataSource.getMovie(movieId)).thenReturn(
            TestDataUtil.movieToMovieEntity(dummyMovie)
        )
        val movie = moviesRepository.getFavouriteMovie(movieId)
        Mockito.verify(moviesLocalDataSource).getMovie(movieId)
        assertEquals(movie, dummyMovie)
    }

    @Test
    fun `insert favourite movie`() = testCoroutineRule.runBlockingTest {
        // Verify method called in LocalDataSource
        // insert Movie
        val movie = favouriteMoviesDummy[0]
        val movieEntity = TestDataUtil.movieToMovieEntity(movie)
        Mockito.`when`(moviesLocalDataSource.addFavouriteMovie(movieEntity)).thenReturn(any())
        moviesRepository.addFavouriteMovie(movie)
        Mockito.verify(moviesLocalDataSource).addFavouriteMovie(movieEntity)
    }

    @Test
    fun `remove favourite movie`() = testCoroutineRule.runBlockingTest {
        // Verify method called in LocalDataSource
        // insert Movie
        val movie = favouriteMoviesDummy[0]
        val movieEntity = TestDataUtil.movieToMovieEntity(movie)
        Mockito.`when`(moviesLocalDataSource.deleteFavouriteMovie(movieEntity)).thenReturn(any())
        moviesRepository.deleteFavouriteMovie(movie)
        Mockito.verify(moviesLocalDataSource).deleteFavouriteMovie(movieEntity)
    }
}