package com.dpfht.android.demomovieflow.feature_favorite_movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.feature_favorite_movies.databinding.FragmentFavoriteMoviesBinding
import com.dpfht.android.demomovieflow.feature_favorite_movies.di.DaggerFavoriteMoviesComponent
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

  private lateinit var binding: FragmentFavoriteMoviesBinding
  private val viewModel by viewModels<FavoriteMoviesViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerFavoriteMoviesComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvMovies.adapter = viewModel.adapter
    viewModel.adapter.onClickMovieCallback = this::onNavigateToMovieDetails

    observeViewModel()
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
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
      }
    }
  }

  private fun onNavigateToMovieDetails(movieEntity: MovieEntity) {
    navigationService.navigateToMovieDetails(movieEntity.id, movieEntity)
  }
}
