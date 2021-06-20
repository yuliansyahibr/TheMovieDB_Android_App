package com.dicoding.tmdbapp.core.data.source.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.tmdbapp.core.data.source.remote.response.PopularMoviesResponse
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.core.util.APIHelper
import com.dicoding.tmdbapp.core.util.DataMapper
import retrofit2.Response

class MoviesPagingSource(
    private val apiCall: suspend (page: Int)->Response<PopularMoviesResponse>
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageIndex = params.key ?: 1
            val response = apiCall(pageIndex)
            val popularMoviesResponse = APIHelper.apiHandler(response)
            val movies: List<Movie> =  popularMoviesResponse.results.map { item ->
                DataMapper.popularMovieItemResponseToMovie(item)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = if (movies.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: Exception) {
            Log.e("MoviesPagingSource", exception.message.toString())
            LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}