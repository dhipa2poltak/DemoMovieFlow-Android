package com.dpfht.android.demomovieflow.feature_favorite_movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.feature_favorite_movies.databinding.FragmentFavoriteMoviesBinding
import com.dpfht.android.demomovieflow.framework.Constants
import com.dpfht.android.demomovieflow.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : BaseFragment<FragmentFavoriteMoviesBinding>(R.layout.fragment_favorite_movies) {

  private val viewModel by viewModels<FavoriteMoviesViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setFragmentResultListener(Constants.FragmentActionKeys.ACTION_KEY_FAVORITE) { _, result ->
      val movieId = result.getInt(Constants.FragmentArgsName.ARG_MOVIE_ID)
      val isFavorite = result.getBoolean(Constants.FragmentArgsName.ARG_IS_FAVORITE)

      viewModel.isFromDetails = true
      viewModel.isRemovingMovie = !isFavorite
      viewModel.movieIdToRemove = movieId
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvMovies.adapter = viewModel.adapter
    viewModel.adapter.onClickMovieCallback = this::onNavigateToMovieDetails

    observeViewModel()
  }

  override fun onResume() {
    super.onResume()
    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { isShow ->
      binding.clProgressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    viewModel.toastMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.modalMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        navigationService.navigateToErrorMessage(msg)
      }
    }

    viewModel.isNoData.observe(viewLifecycleOwner) { isNoData ->
      if (isNoData) {
        binding.rvMovies.visibility = View.INVISIBLE
        binding.tvNoData.visibility = View.VISIBLE
      } else {
        binding.rvMovies.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
      }
    }
  }

  private fun onNavigateToMovieDetails(movieEntity: MovieEntity) {
    navigationService.navigateToMovieDetails(movieEntity.id, movieEntity, true)
  }
}
