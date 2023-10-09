package com.dpfht.android.demomovieflow.feature_favorite_movies.di

import android.content.Context
import com.dpfht.android.demomovieflow.feature_favorite_movies.FavoriteMoviesFragment
import com.dpfht.android.demomovieflow.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface FavoriteMoviesComponent {

  fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): FavoriteMoviesComponent
  }
}
