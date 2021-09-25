package com.davilav.wigilabstest.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davilav.wigilabstest.R
import com.davilav.wigilabstest.data.model.Movie
import com.davilav.wigilabstest.databinding.ActivityMovieBinding
import com.davilav.wigilabstest.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()
    private var movies: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            movies = it.getSerializableExtra(MOVIE_KEY) as Movie?
        }
        setUpClickListener()
        setUpObserver()
    }

    private fun setUpObserver() {
        TODO("Not yet implemented")
    }

    private fun setUpClickListener() {
        TODO("Not yet implemented")
    }


    companion object {
        const val MOVIE_KEY = Constants.MOVIE_KEY
    }
}