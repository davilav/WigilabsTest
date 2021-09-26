package com.davilav.wigilabstest.ui.movie

import com.davilav.wigilabstest.data.Result
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse
import com.davilav.wigilabstest.data.repository.movie.MovieRepositoryImpl
import com.davilav.wigilabstest.utils.Constants
import com.davilav.wigilabstest.utils.SingleLiveEvent
import com.davilav.wigilabstest.utils.Singleton
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MovieViewModel(
    private val repository: MovieRepositoryImpl
) : ViewModel() {

    private val _dataResponseOnline: MutableLiveData<Pair<Boolean, Any?>> = MutableLiveData()
    val dataResponseOnline: LiveData<Pair<Boolean, Any?>> get() = _dataResponseOnline
    private val _dataResponseOffline: MutableLiveData<Pair<Boolean, Any?>> = MutableLiveData()
    val dataResponseOffline: LiveData<Pair<Boolean, Any?>> get() = _dataResponseOffline
    private val _dataResponse: SingleLiveEvent<MovieState> = SingleLiveEvent()
    val dataResponse: SingleLiveEvent<MovieState> get() = _dataResponse

    private fun getApiKey(): String = Constants.API_KEY

    private fun getLanguage(): String = TODO("Hacer la lógica que permita capturar el lenguaje que eligio el usuario para que se vuelva a consumir la api")

    fun getMovie() {
        viewModelScope.launch {
            when (val result = repository.getMovie(getApiKey(), getLanguage())) {
                is NetworkResponse.Success -> {
                    _dataResponse.value = MovieState.MovieSuccess(result.body.results)
                }
                else -> _dataResponse.value = MovieState.MovieFailure
            }
        }
    }

    fun getMovieOffline() {
        viewModelScope.launch {
            val response = repository.getMoviesDB()
            when (response.status) {
                Result.Status.SUCCESS -> {
                    _dataResponseOffline.value = Pair(true, response.data)
                }
                Result.Status.ERROR -> {
                    _dataResponseOffline.value = Pair(false, "Error")
                }
            }
        }
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network?.isConnected) ?: false
    }

    fun isOnline(context: Context) {
        AsyncTask.execute {
            try {
                if (hasNetworkAvailable(context)) {
                    try {
                        val connection =
                            URL(Constants.REACHABILITY_SERVER).openConnection() as HttpURLConnection
                        connection.setRequestProperty("User-Agent", "Test")
                        connection.setRequestProperty("Connection", "close")
                        connection.connectTimeout = 1500
                        connection.connect()
                        val myResponse = (connection.responseCode in 200..299)
                        Singleton.isOnline = myResponse
                        _dataResponseOnline.postValue(Pair(true, "Connection is successfully"))

                    } catch (e: IOException) {

                    }
                } else {
                    _dataResponseOnline.postValue(Pair(false, "No network available"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}