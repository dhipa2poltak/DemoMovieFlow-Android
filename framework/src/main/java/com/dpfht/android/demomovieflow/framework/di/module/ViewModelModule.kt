package com.dpfht.android.demomovieflow.framework.di.module

import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import com.dpfht.android.demomovieflow.domain.usecase.AddFavoriteMovieUseCase
import com.dpfht.android.demomovieflow.domain.usecase.AddFavoriteMovieUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.android.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.GetAllFavoriteMoviesUseCase
import com.dpfht.android.demomovieflow.domain.usecase.GetAllFavoriteMoviesUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.GetFavoriteMovieUseCase
import com.dpfht.android.demomovieflow.domain.usecase.GetFavoriteMovieUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.android.demomovieflow.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.GetPopularMoviesUseCase
import com.dpfht.android.demomovieflow.domain.usecase.GetPopularMoviesUseCaseImpl
import com.dpfht.android.demomovieflow.domain.usecase.SearchMovieUseCase
import com.dpfht.android.demomovieflow.domain.usecase.SearchMovieUseCaseImpl
import com.dpfht.android.demomovieflow.framework.commons.model.FavoriteMovieCacheModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

  @Provides
  fun provideGetMovieDetailsUseCase(appRepository: AppRepository): GetMovieDetailsUseCase {
    return GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetPopularMoviesUseCase(appRepository: AppRepository): GetPopularMoviesUseCase {
    return GetPopularMoviesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideSearchMovieUseCase(appRepository: AppRepository): SearchMovieUseCase {
    return SearchMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetAllFavoriteMoviesUseCase(appRepository: AppRepository): GetAllFavoriteMoviesUseCase {
    return GetAllFavoriteMoviesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetFavoriteMovieUseCase(appRepository: AppRepository): GetFavoriteMovieUseCase {
    return GetFavoriteMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideAddFavoriteMovieUseCase(appRepository: AppRepository): AddFavoriteMovieUseCase {
    return AddFavoriteMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideDeleteFavoriteMovieUseCase(appRepository: AppRepository): DeleteFavoriteMovieUseCase {
    return DeleteFavoriteMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideArrayListFavoriteMovieCacheModel(): ArrayList<FavoriteMovieCacheModel> {
    return arrayListOf()
  }
}
