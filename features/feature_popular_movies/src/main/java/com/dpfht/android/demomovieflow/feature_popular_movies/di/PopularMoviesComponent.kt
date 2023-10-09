package com.dpfht.android.demomovieflow.feature_popular_movies.di

import android.content.Context
import com.dpfht.android.demomovieflow.feature_popular_movies.PopularMoviesFragment
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface PopularMoviesComponent {

  fun inject(popularMoviesFragment: PopularMoviesFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): PopularMoviesComponent
  }
}
