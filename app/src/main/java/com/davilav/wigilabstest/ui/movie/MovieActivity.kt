package com.davilav.wigilabstest.ui.movie

import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.databinding.ActivityMovieBinding
import com.davilav.wigilabstest.ui.adapter.MovieAdapter
import com.davilav.wigilabstest.ui.dialog.CustomAlertDialog
import com.davilav.wigilabstest.ui.dialog.FilmDialog
import com.davilav.wigilabstest.utils.Constants
import com.davilav.wigilabstest.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.graphics.drawable.PictureDrawable

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import com.davilav.wigilabstest.R
import com.davilav.wigilabstest.utils.RoundCornersBitmap


class MovieActivity : AppCompatActivity() {

    private var recyclerMovie: RecyclerView? = null
    private var mAdapter: MovieAdapter? = null
    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()
    private var movies: MovieModel? = null
    private var dataList: List<MovieModel> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            movies = it.getSerializableExtra(MOVIE_KEY) as MovieModel?
        }
        viewModel.getMovie(binding.button.text as String, this)
        setupRecyclerView(true)
        setUpClickListener()
        setUpObserver()
    }

    private fun setupRecyclerView(hasConnection:Boolean) {
        recyclerMovie = binding.rvMovie
        mAdapter = MovieAdapter(dataList, viewModel, ::callbackMovieDetail, ::callbackMovieDownload,hasConnection)
        recyclerMovie?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = mAdapter
        }
    }

    private fun switchLanguage(language: String) {
        if (language == "en-US") {
            binding.button.text = getString(R.string.spanish)
        } else {
            binding.button.text = getString(R.string.english)
        }
    }

    private fun setUpObserver() {
        viewModel.dataResponseOnline.observe(this, { response ->
            when (response.first) {
                true -> viewModel.getMovie(binding.button.text as String, this)
                false -> viewModel.getMovieOffline()
            }
        })

        viewModel.dataResponseOffline.observe(this, { response ->
            when (response.first) {
                true -> {
                    var list = arrayListOf<MovieModel>()
                    var base_list = response.second as List<Movie>
                    base_list.forEach {x ->
                       list.add(Utils().toMovieModel(x))
                    }
                    list.forEach { x ->
                        var image = BitmapFactory.decodeResource(resources, R.drawable.mynotview)
                        x.poster_img =RoundCornersBitmap(Bitmap.createScaledBitmap(image, 240 ,340, false),40).roundedCornerBitmap()
                    }
                    dataList = list
                    setupRecyclerView(false)
                }
                false -> onFailureNetworkConnection()
            }
        })

        viewModel.dataResponse.observe(this, { response ->
            when (response) {
                is MovieState.MovieSuccess -> mAdapter?.updateData(response.movies!!)
                is MovieState.MovieFailure -> viewModel.getMovieOffline()
                is MovieState.Loading -> TODO()
            }
        })
    }

    private fun setUpClickListener() {
        binding.button.setOnClickListener {
            switchLanguage(binding.button.text as String)
            viewModel.getMovie(binding.button.text as String, this)
        }

        binding.button.setOnClickListener {
            switchLanguage(binding.button.text as String)
            viewModel.getMovie(binding.button.text as String, this)
        }
    }

    private fun callbackMovieDetail(movie: MovieModel) {
        FilmDialog.instance(movie) {
        }.show(supportFragmentManager, FilmDialog::class.java.canonicalName)

    }

    private fun callbackMovieDownload(movie: MovieModel) {
        viewModel.downloadMovie(
           Utils().toMovie(movie), this
        )
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
    }

    private fun onFailureNetworkConnection() {
        val securityAlert = CustomAlertDialog.instance(
            getString(R.string.network_connection_title),
            getString(R.string.network_connection_message),
            getString(R.string.try_again_button),
            getString(R.string.exit_button),
            { viewModel.isOnline(applicationContext,Constants.REACHABILITY_SERVER) },
            { Process.killProcess(Process.myPid()) }
        )
        securityAlert.show(
            supportFragmentManager,
            CustomAlertDialog::class.java.simpleName
        )
    }

    companion object {
        const val MOVIE_KEY = Constants.MOVIE_KEY
    }
}