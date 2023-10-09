package com.dpfht.android.demomovieflow.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dpfht.android.demomovieflow.R
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import com.dpfht.android.demomovieflow.navigation.NavigationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

  @Provides
  fun provideNavController(@ActivityContext context: Context): NavController {
    val navHostFragment =  (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    return navHostFragment.navController
  }

  @Provides
  fun provideNavigationService(navController: NavController, @ActivityContext context: Context): NavigationService {
    return NavigationServiceImpl(navController, context as AppCompatActivity)
  }
}
