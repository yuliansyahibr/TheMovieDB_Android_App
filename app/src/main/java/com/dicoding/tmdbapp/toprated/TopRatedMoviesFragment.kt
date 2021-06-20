package com.dicoding.tmdbapp.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.tmdbapp.core.ui.MoviesPagingAdapter
import com.dicoding.tmdbapp.databinding.MovieListFragmentBinding
import com.dicoding.tmdbapp.util.FragmentExt.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {

    private val viewModel: TopRatedMoviesViewModel by viewModels()
    private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private var fetchJob: Job? = null
    @Inject
    lateinit var rvAdapter: MoviesPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        rvAdapter.setOnItemClickListener { movie ->
            movie?.let {
                findNavController().navigate(
                    TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it)
                )
            }
        }

        setUpAdapter()
        startFetchJob()
    }

    private fun startFetchJob() {
        fetchJob?.cancel()
        fetchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getList().observe(viewLifecycleOwner, {
                rvAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {

        binding.rvMovies.apply {
            adapter = rvAdapter
            setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(requireContext())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rvAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.listProgressBar.isVisible = loadStates.refresh is LoadState.Loading
                showError(loadStates.refresh is LoadState.Error)
            }
        }

    }

    private fun showError(state: Boolean) {
        if (state) {
            toast("Error while fetching data")
            binding.viewError.root.visibility = View.VISIBLE
        } else {
            binding.viewError.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
        _binding = null
    }
}