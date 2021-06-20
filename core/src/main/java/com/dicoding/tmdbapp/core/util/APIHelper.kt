package com.dicoding.tmdbapp.core.util

import retrofit2.Response

object APIHelper {
    fun <T> apiHandler(response: Response<T>): T {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return result
            }
        }
        throw Exception(response.message())
    }
}