package com.davilav.wigilabstest.data.repository.movie

import com.davilav.wigilabstest.data.Result
import com.davilav.wigilabstest.data.local.db.LocalDataBase
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.ErrorResponse
import com.davilav.wigilabstest.data.model.Page
import com.davilav.wigilabstest.data.remote.ApiInterface
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse

class MovieRepositoryImpl(
    private val apiClient: ApiInterface,
    private val database: LocalDataBase
) : MovieRepository {
    override suspend fun getMovie(api_key: String, language: String): NetworkResponse<Page, ErrorResponse> =
        apiClient.getMovies(api_key, language)

    override suspend fun getMoviesDB(): Result<Any> {
        val data = database.getMovieDao().getMovie()
        return if(data.isNotEmpty()){
            Result.success(data)
        } else {
            Result.error("Error")
        }
    }

    override suspend fun compareMovies(movie: Movie) {
        val data = database.getMovieDao().getMovie()
        if (data.isEmpty() || !data.contains(movie)) {
            insertMovies(movie)
        }
    }

    override suspend fun insertMovies(movie: Movie) {
        database.getMovieDao().insertMovie(movie)
    }
}
