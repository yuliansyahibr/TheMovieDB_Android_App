package com.dicoding.tmdbapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.tmdbapp.core.databinding.ItemMovieBinding
import com.dicoding.tmdbapp.core.domain.model.Movie

class MovieRVAdapter : RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>() {

    private var listFilm = listOf<Movie>()
    private lateinit var onItemClickListener: (Movie?) -> Unit

    fun setOnItemClickListener(listener: (Movie?) -> Unit) {
        onItemClickListener = listener
    }

    fun setList(list: List<Movie>) {
        listFilm = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listFilm[position])
    }

    override fun getItemCount() = listFilm.size

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: (Movie?)->Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            movie?.let {
                with(binding) {
                    tvItemTitle.text = movie.title
                    tvItemReleaseDate.text = movie.releaseDate
                    tvItemOverview.text = movie.overview
                    Glide.with(itemView)
                        .load(movie.getPosterImageURL())
                        .into(imgItemPoster)
                }
//            binding.root.setOnClickListener {  }
                itemView.setOnClickListener {
                    onItemClickListener(movie)
                }
            }
        }

    }
}