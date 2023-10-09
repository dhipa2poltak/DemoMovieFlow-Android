package com.dpfht.android.demomovieflow.feature_movie_details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dpfht.android.demomovieflow.feature_movie_details.databinding.FragmentMovieDetailsBinding
import com.dpfht.android.demomovieflow.feature_movie_details.di.DaggerMovieDetailsComponent
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

  private lateinit var binding: FragmentMovieDetailsBinding
  private val viewModel by viewModels<MovieDetailsViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerMovieDetailsComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    arguments?.let {
      val movieId = it.getInt("movieId")
      if (movieId != -1) {
        observeViewModel()
        setListener()

        viewModel.movieId = movieId
        viewModel.start()
      }
    }
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

    viewModel.movieData.observe(viewLifecycleOwner) { movieEntity ->
      binding.tvTitleMovie.text = movieEntity.title
      binding.tvDescMovie.text = movieEntity.overview

      if (movieEntity.imageUrl.isNotEmpty()) {
        Picasso.get().load(movieEntity.imageUrl).into(binding.ivImageMovie)
      }
    }

    viewModel.isFavoriteData.observe(viewLifecycleOwner) { isFavorite ->
      if (isFavorite) {
        binding.btnAddFavorite.visibility = View.GONE
        binding.btnDeleteFavorite.visibility = View.VISIBLE
      } else {
        binding.btnAddFavorite.visibility = View.VISIBLE
        binding.btnDeleteFavorite.visibility = View.GONE
      }
    }
  }

  private fun setListener() {
    binding.btnAddFavorite.setOnClickListener {
      viewModel.addFavoriteMovie()
    }

    binding.btnDeleteFavorite.setOnClickListener {
      viewModel.deleteFavoriteMovie()
    }
  }
}
