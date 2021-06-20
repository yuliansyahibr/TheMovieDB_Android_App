package com.dicoding.tmdbapp.core.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.tmdbapp.core.databinding.ItemMovieBinding
import com.dicoding.tmdbapp.core.domain.model.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClickListener: (Movie?)->Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, position: Int? = null) {
        movie?.let {
            with(binding) {
                position?.let {
                    val pos = "#${it+1}"
                    tvRankingNumber.text = pos
                }
                tvItemTitle.text = movie.title
                tvItemReleaseDate.text = movie.releaseDate
                tvItemOverview.text = movie.overview
                Glide.with(itemView)
                    .load(movie.getPosterImageURL())
                    .into(imgItemPoster)
            }
            itemView.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }

}