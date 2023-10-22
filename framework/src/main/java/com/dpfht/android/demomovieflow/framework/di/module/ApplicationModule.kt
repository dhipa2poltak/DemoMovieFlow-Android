package com.dpfht.android.demomovieflow.framework.di.module

import com.dpfht.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.demomovieflow.data.repository.AppRepositoryImpl
import com.dpfht.demomovieflow.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideAppRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): AppRepository {
    return AppRepositoryImpl(localDataSource, remoteDataSource)
  }
}
