package com.dpfht.android.demomovieflow.feature_splash.di

import android.content.Context
import com.dpfht.android.demomovieflow.feature_splash.SplashFragment
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface SplashComponent {

  fun inject(splashFragment: SplashFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): SplashComponent
  }
}
