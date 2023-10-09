package com.dpfht.android.demomovieflow.feature_splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.android.demomovieflow.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.android.demomovieflow.feature_splash.di.DaggerSplashComponent
import com.dpfht.android.demomovieflow.framework.Constants
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

  private lateinit var binding: FragmentSplashBinding

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerSplashComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

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
