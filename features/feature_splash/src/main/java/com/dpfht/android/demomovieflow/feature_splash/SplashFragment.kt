package com.dpfht.android.demomovieflow.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.android.demomovieflow.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.android.demomovieflow.framework.Constants
import com.dpfht.android.demomovieflow.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Handler(Looper.getMainLooper()).postDelayed({
      navigationService.navigateToMovieHome()
    }, Constants.DELAY_SPLASH)
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
