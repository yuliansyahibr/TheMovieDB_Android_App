package com.dicoding.tmdbapp.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.tmdbapp.core.data.source.local.LocalMoviesDataSource
import com.dicoding.tmdbapp.core.data.source.local.room.MoviesDatabase
import com.dicoding.tmdbapp.core.data.source.remote.RemoteMoviesDataSource
import com.dicoding.tmdbapp.core.data.source.remote.network.TMDBService
import com.dicoding.tmdbapp.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MoviesDatabase::class.java,
        "movies.db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideTMDBService(): TMDBService {
        val headersInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("User-Agent", "Android")
                .build()
            chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(headersInterceptor)
        }.build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TMDBService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        db: MoviesDatabase
    ): LocalMoviesDataSource {
        return LocalMoviesDataSource(
            moviesDatabase = db
        )
    }
//
    @Singleton
    @Provides
    fun provideRemoteDataSource(
        api: TMDBService,
    ): RemoteMoviesDataSource {
        return RemoteMoviesDataSource(api)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}