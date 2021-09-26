package com.davilav.wigilabstest.data.model

data class Page(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<MovieModel>?
)