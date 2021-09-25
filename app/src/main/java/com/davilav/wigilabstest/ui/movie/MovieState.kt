package com.davilav.wigilabstest.ui.movie

sealed class MovieState {
    data class Loading(var loading : Boolean) : MovieState()
    object MovieSuccess: MovieState()
    object MovieFailure: MovieState()
}