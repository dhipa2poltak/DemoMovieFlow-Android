package com.dpfht.android.demomovieflow.feature_movie_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.dpfht.android.demomovieflow.feature_movie_details.databinding.FragmentMovieDetailsBinding
import com.dpfht.android.demomovieflow.framework.Constants
import com.dpfht.android.demomovieflow.framework.commons.base.BaseFragment
import com.dpfht.android.demomovieflow.framework.commons.model.MovieArgModel
import com.dpfht.android.demomovieflow.framework.commons.model.toDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import com.dpfht.android.demomovieflow.framework.R as frameworkR


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

  private val viewModel by viewModels<MovieDetailsViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    arguments?.let {
      val movieId = it.getInt(Constants.FragmentArgsName.ARG_MOVIE_ID)
      if (movieId != -1) {
        viewModel.movieId = movieId

        val strModel = it.getString(Constants.FragmentArgsName.ARG_MOVIE_MODEL) ?: ""
        if (strModel.isNotEmpty()) {
          val typeToken = object : TypeToken<MovieArgModel>() {}.type
          val movieModel = Gson().fromJson<MovieArgModel>(strModel, typeToken)
          viewModel.movieEntity = movieModel.toDomain()
        }

        viewModel.isForResult = it.getBoolean(Constants.FragmentArgsName.ARG_IS_FOR_RESULT)

        observeViewModel()
        setListener()

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
        navigationService.navigateToErrorMessage(msg)
      }
    }

    viewModel.titleData.observe(viewLifecycleOwner) { title ->
      binding.tvTitleMovie.text = title
    }

    viewModel.overviewData.observe(viewLifecycleOwner) { overview ->
      binding.tvDescMovie.text = overview
    }

    viewModel.imageUrlData.observe(viewLifecycleOwner) { imageUrl ->
      if (imageUrl.isNotEmpty()) {
        Picasso.get().load(imageUrl)
          .error(frameworkR.drawable.no_image)
          .placeholder(frameworkR.drawable.no_image)
          .into(binding.ivImageMovie)
      } else {
        binding.ivImageMovie.setImageResource(frameworkR.drawable.no_image)
      }
    }

    viewModel.genres.observe(viewLifecycleOwner) { genres ->
      binding.tvGenresMovie.text = genres


      binding.tvGenresMovie.visibility = if (genres.isNotEmpty()) {
        View.VISIBLE
      } else {
        View.INVISIBLE
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

    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
  }

  private val onBackPressedCallback = object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
      if (viewModel.isForResult) {
        val result = Bundle()
        result.putInt(Constants.FragmentArgsName.ARG_MOVIE_ID, viewModel.movieId)
        result.putBoolean(Constants.FragmentArgsName.ARG_IS_FAVORITE, viewModel.isFavoriteData.value ?: false)

        setFragmentResult(Constants.FragmentActionKeys.ACTION_KEY_FAVORITE, result)
      }

      navigationService.navigateUp()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    onBackPressedCallback.isEnabled = false
    onBackPressedCallback.remove()
  }
}
