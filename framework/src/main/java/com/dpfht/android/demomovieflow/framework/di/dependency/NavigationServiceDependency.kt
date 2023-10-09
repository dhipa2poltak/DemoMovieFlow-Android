package com.dpfht.android.demomovieflow.framework.di.dependency

import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface NavigationServiceDependency {

  fun getNavigationService(): NavigationService
}
