package com.davilav.wigilabstest.ui.movie

import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davilav.wigilabstest.R
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.databinding.ActivityMovieBinding
import com.davilav.wigilabstest.ui.adapter.MovieAdapter
import com.davilav.wigilabstest.ui.dialog.CustomAlertDialog
import com.davilav.wigilabstest.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        viewModel.getMovie()
        setupRecyclerView()
        setUpClickListener()
        setUpObserver()
    }

    private fun setupRecyclerView() {
        recyclerMovie = binding.rvMovie
        mAdapter = MovieAdapter(dataList, viewModel, ::callbackMovieDetail)
        recyclerMovie?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = mAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.dataResponseOnline.observe(this, { response ->
            when (response.first) {
                true -> viewModel.getMovie()
                false -> viewModel.getMovieOffline()
            }
        })

        viewModel.dataResponseOffline.observe(this, { response ->
            when (response.first) {
                true -> {
                    dataList = response.second as List<MovieModel>
                }
                false -> onFailureNetworkConnection()
            }
        })

        viewModel.dataResponse.observe(this, { response ->
            when (response) {
                is MovieState.MovieSuccess -> mAdapter?.updateData(response.movies!!)
                is MovieState.Loading -> TODO()
                is MovieState.MovieFailure -> TODO()
            }
        })
    }

    private fun setUpClickListener() {
        binding.tvTitle.setOnClickListener {
            binding.tvTitle.text = "Es-es"
        }
    }

    private fun callbackMovieDetail(movie: MovieModel) {
        TODO("Hacer la lógica que permita mostrar la información detallada cuando el usuario toque una card en especifico")
    }

    private fun onFailureNetworkConnection() {
        val securityAlert = CustomAlertDialog.instance(
            getString(R.string.network_connection_title),
            getString(R.string.network_connection_message),
            getString(R.string.try_again_button),
            getString(R.string.exit_button),
            { viewModel.isOnline(applicationContext) },
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