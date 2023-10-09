package com.dpfht.android.demomovieflow.feature_search_movie.di

import android.content.Context
import com.dpfht.android.demomovieflow.feature_search_movie.SearchMovieFragment
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface SearchMovieComponent {

  fun inject(searchMovieFragment: SearchMovieFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): SearchMovieComponent
  }
}
