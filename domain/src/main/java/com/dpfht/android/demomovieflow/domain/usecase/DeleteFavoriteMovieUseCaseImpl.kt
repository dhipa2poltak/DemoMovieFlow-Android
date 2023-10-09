package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class DeleteFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): DeleteFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult> {
    return appRepository.deleteFavoriteMovie(movie)
  }
}
