package com.dicoding.tmdbapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dicoding.tmdbapp.core.databinding.ItemMovieBinding
import com.dicoding.tmdbapp.core.domain.model.Movie

class MoviesPagingAdapter : PagingDataAdapter<Movie, MovieRVAdapter.MovieViewHolder>(
    FilmComparator()
) {

    private lateinit var onItemClickListener: (Movie?) -> Unit

    fun setOnItemClickListener(listener: (Movie?) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieRVAdapter.MovieViewHolder {
        return MovieRVAdapter.MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.MovieViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item)
    }

//    inner class MovieViewHolder(
//        private val binding: ItemMovieBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(movie: Movie?) {
//            movie?.let {
//                with(binding) {
//                    tvItemTitle.text = it.title
//                    tvItemReleaseDate.text = it.releaseDate
//                    tvItemOverview.text = it.overview
//                    Glide.with(itemView)
//                        .load(it.getPosterImageURL())
//                        .into(imgItemPoster)
//                }
//            }
//            itemView.setOnClickListener {
//                onItemClickListener(movie)
//            }
//        }
//    }

    private class FilmComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}