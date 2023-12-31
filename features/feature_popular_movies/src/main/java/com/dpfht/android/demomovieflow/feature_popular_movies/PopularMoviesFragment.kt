package com.dpfht.android.demomovieflow.feature_popular_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.feature_popular_movies.databinding.FragmentPopularMoviesBinding
import com.dpfht.android.demomovieflow.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding>(R.layout.fragment_popular_movies) {

  private val viewModel by viewModels<PopularMoviesViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvMovies.adapter = viewModel.adapter
    viewModel.adapter.onClickMovieCallback = this::navigateToMovieDetails

    observeViewModel()
    setListener()
    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isNoData.observe(viewLifecycleOwner) { isNoData ->
      if (isNoData) {
        binding.rvMovies.visibility = View.INVISIBLE
        binding.tvNoData.visibility = View.VISIBLE
      } else {
        binding.rvMovies.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
      }
    }

    viewModel.modalMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        navigationService.navigateToErrorMessage(msg)
      }
    }
  }

  private fun setListener() {
    viewModel.adapter.addLoadStateListener {
      binding.clProgressBar.visibility = when (it.refresh) {
        LoadState.Loading -> {
          View.VISIBLE
        }
        else -> {
          View.GONE
        }
      }
    }
  }

  private fun navigateToMovieDetails(movieEntity: MovieEntity) {
    navigationService.navigateToMovieDetails(movieEntity.id)
  }
}
