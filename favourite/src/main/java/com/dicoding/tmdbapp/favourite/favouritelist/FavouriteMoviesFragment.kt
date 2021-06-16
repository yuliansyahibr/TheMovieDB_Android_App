package com.dicoding.tmdbapp.favourite.favouritelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdbapp.MainActivity
import com.dicoding.tmdbapp.core.ui.MovieRVAdapter
import com.dicoding.tmdbapp.R
import com.dicoding.tmdbapp.favourite.di.DaggerFavouriteComponent
import com.dicoding.tmdbapp.di.FavouriteModuleDependencies
import com.dicoding.tmdbapp.favourite.databinding.FavouriteMovieListFragmentBinding
import com.dicoding.tmdbapp.util.FragmentExt.setUpLoader
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment() {

    @Inject
    lateinit var viewModel: FavouriteMoviesViewModel
    private var _binding: FavouriteMovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: MovieRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjection()
        super.onCreate(savedInstanceState)
        viewModel.getFavouriteMovies()
    }

    private fun initInjection() {
        val moduleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            FavouriteModuleDependencies::class.java
        )
        DaggerFavouriteComponent.factory().create(moduleDependencies).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouriteMovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = getString(R.string.title_favourite)
        (activity as MainActivity).supportActionBar?.title = title

        hideEmptyErrorText()
        setUpLoader(binding.listProgressBar, viewModel.loading)

        rvAdapter = MovieRVAdapter()
        rvAdapter.setOnItemClickListener { movie ->
            movie?.let {
                findNavController().navigate(
                    FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToMovieDetailFragment(it)
                )
            }
        }
        binding.rvDaftarFilm.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.list.observe(viewLifecycleOwner, {
            rvAdapter.setList(it)
            if(it.isEmpty()) {
                showEmptyErrorText()
            }
        })

    }

    private fun hideEmptyErrorText() {
        binding.tvErrorFavourites.visibility = View.GONE
    }

    private fun showEmptyErrorText() {
        binding.tvErrorFavourites.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}