package com.dicoding.tmdbapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.tmdbapp.core.databinding.ItemMovieBinding
import com.dicoding.tmdbapp.core.domain.model.Movie

class MovieRVAdapter : RecyclerView.Adapter<MovieViewHolder>() {

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
        holder.bind(listFilm[position], null)
    }

    override fun getItemCount() = listFilm.size

}