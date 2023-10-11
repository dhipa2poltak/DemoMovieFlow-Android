package com.dpfht.android.demomovieflow.feature_popular_movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.feature_popular_movies.databinding.FragmentPopularMoviesBinding
import com.dpfht.android.demomovieflow.feature_popular_movies.di.DaggerPopularMoviesComponent
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

  private lateinit var binding: FragmentPopularMoviesBinding
  private val viewModel by viewModels<PopularMoviesViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerPopularMoviesComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)

    return binding.root
  }

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
