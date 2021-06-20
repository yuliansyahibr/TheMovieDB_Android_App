package com.dicoding.tmdbapp.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dicoding.tmdbapp.MainActivity
import com.dicoding.tmdbapp.R
import com.dicoding.tmdbapp.core.domain.model.Movie
import com.dicoding.tmdbapp.databinding.MovieDetailFragmentBinding
import com.dicoding.tmdbapp.util.FragmentExt.setUpLoader
import com.dicoding.tmdbapp.util.FragmentExt.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val title = ""
        val mainActivity = requireActivity() as MainActivity
        mainActivity.supportActionBar?.title = title
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.binding.bottomNavView.visibility = View.GONE

        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie
        fab = binding.btnFav
        setUpLoader(binding.detailProgressBar, viewModel.loading)

        viewModel.getMovie(movie.id).observe(viewLifecycleOwner, {
            if(it != null) {
                (activity as MainActivity).supportActionBar?.title = it.getFullTitle()
                setDetailView(view, it)
            }
        })
        viewModel.addedToFavourites.observe(viewLifecycleOwner, { added ->
            if(added) {
                changeFabToDeleteState()
                toast("Added to favourites")
            }
        })
        viewModel.removedFromFavourites.observe(viewLifecycleOwner, { removed ->
            if(removed) {
                changeFabToAddState()
                toast("Removed from favourites")
            }
        })
        viewModel.isFavourite.observe(viewLifecycleOwner, { isFavourite ->
            if(isFavourite) {
                changeFabToDeleteState()

            } else {
                changeFabToAddState()
            }
        })

        binding.btnFav.setOnClickListener {
            viewModel.btnAction()
        }

    }

    private fun changeFabToAddState() {
        fab.setImageResource(R.drawable.ic_round_favorite_border_24)
    }

    private fun changeFabToDeleteState() {
        fab.setImageResource(R.drawable.ic_round_favorite_24)
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailView(view: View, movie: Movie) {
        with(binding) {
            tvTitle.text = movie.getFullTitle()
            tvType.text = getString(R.string.type)
            tvReleaseDate.text = movie.releaseDate
            tvDuration.text = movie.getDuration()
            tvUserScore.text = movie.userScore.toString()
            tvUserCount.text = movie.userCount.toString()
            tvGenres.text = movie.genres
            tvOverview.text = movie.overview
            tvStatus.text = movie.status
            tvLanguage.text = movie.originalLanguage
            if(movie.poster.isNotBlank()) {
                Glide.with(view)
                    .load(movie.getPosterImageURL())
                    .into(imgPoster)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).binding.bottomNavView.visibility = View.VISIBLE
        _binding = null
    }

}