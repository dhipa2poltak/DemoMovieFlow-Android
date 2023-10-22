package com.dpfht.android.demomovieflow.framework.di.module

import android.content.Context
import com.dpfht.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.android.demomovieflow.framework.data.datasource.local.LocalDataSourceImpl
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.db.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

  @Provides
  @Singleton
  fun provideAppDB(@ApplicationContext context: Context): AppDB {
    return AppDB.getDatabase(context)
  }

  @Provides
  @Singleton
  fun provideLocalDataSource(@ApplicationContext context: Context, appDB: AppDB): LocalDataSource {
    return LocalDataSourceImpl(context, appDB)
  }
}
