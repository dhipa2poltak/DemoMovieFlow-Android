package com.dpfht.android.demomovieflow.feature_search_movie

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
import com.dpfht.android.demomovieflow.feature_search_movie.databinding.FragmentSearchMovieBinding
import com.dpfht.android.demomovieflow.feature_search_movie.di.DaggerSearchMovieComponent
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

  private lateinit var binding: FragmentSearchMovieBinding
  private val viewModel by viewModels<SearchMovieViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerSearchMovieComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSearchMovieBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    observeViewModel()
    setListener()
  }

  private fun observeViewModel() {
    viewModel.setAdapterData.observe(viewLifecycleOwner) {
      if (it) {
        binding.rvMovies.adapter = viewModel.adapter
        viewModel.adapter.onClickMovieCallback = this@SearchMovieFragment::navigateToMovieDetails
        viewModel.adapter.addLoadStateListener { loadState ->
          binding.clProgressBar.visibility = when (loadState.refresh) {
            LoadState.Loading -> {
              View.VISIBLE
            }
            else -> {
              View.GONE
            }
          }
        }
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

    viewModel.modalMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        navigationService.navigateToErrorMessage(msg)
      }
    }
  }

  private fun setListener() {
    binding.btnSearch.setOnClickListener {
      if (binding.etSearch.text.toString().trim().isEmpty()) {
        binding.etSearch.error = requireContext().getString(R.string.search_movie_text_query)
        return@setOnClickListener
      }

      viewModel.onSearchMovie(binding.etSearch.text.toString().trim())
    }
  }

  private fun navigateToMovieDetails(movieEntity: MovieEntity) {
    navigationService.navigateToMovieDetails(movieEntity.id)
  }
}
