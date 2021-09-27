package com.davilav.wigilabstest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.databinding.FilmLayoutBinding
import com.davilav.wigilabstest.ui.movie.MovieViewModel

class MovieAdapter(
    items: List<MovieModel>,
    private val viewModel: MovieViewModel,
    private val listenerDetail: (MovieModel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    private var filterResults: MutableList<MovieModel> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val viewBinding = FilmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mContext, viewBinding, viewModel)
    }

    fun updateData(data: List<MovieModel>) {
        filterResults.clear()
        filterResults.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filterResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindData(filterResults[position], listenerDetail)

    class ViewHolder(private val context: Context, private val binding: FilmLayoutBinding, val viewModel: MovieViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: MovieModel, listenerDetail: (MovieModel) -> Unit) {
            binding.tvMovieTitle.text = data.originalTitle
            binding.tvOverview.text = data.overview
            //binding.ivPoster
        }
    }
}