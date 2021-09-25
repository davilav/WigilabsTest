package com.davilav.wigilabstest.data.repository.movie

import com.davilav.wigilabstest.data.model.ErrorResponse
import com.davilav.wigilabstest.data.model.Movie
import com.davilav.wigilabstest.data.remote.ApiInterface
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse

class MovieRepositoryImpl(
    private val apiClient: ApiInterface
) : MovieRepository {
    override suspend fun getMovie(api_key: String, language: String): NetworkResponse<List<Movie>, ErrorResponse> =
        apiClient.getMovies(api_key, language)
}