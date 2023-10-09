package com.dpfht.android.demomovieflow.feature_movie_details.di

import android.content.Context
import com.dpfht.android.demomovieflow.feature_movie_details.MovieDetailsFragment
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface MovieDetailsComponent {

  fun inject(movieDetailsFragment: MovieDetailsFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): MovieDetailsComponent
  }
}
