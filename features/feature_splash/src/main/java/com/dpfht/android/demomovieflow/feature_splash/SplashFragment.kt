package com.dpfht.android.demomovieflow.feature_splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dpfht.android.demomovieflow.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.android.demomovieflow.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

  private val viewModel by viewModels<SplashViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewModel()
    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.hasFinishedDelaying.observe(viewLifecycleOwner) { hasFinished ->
      if (hasFinished) {
        navigateToNextScreen()
      }
    }
  }

  private fun navigateToNextScreen() {
    navigationService.navigateToMovieHome()
  }

  override fun onStart() {
    super.onStart()
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
  }

  override fun onStop() {
    super.onStop()
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
  }
}
