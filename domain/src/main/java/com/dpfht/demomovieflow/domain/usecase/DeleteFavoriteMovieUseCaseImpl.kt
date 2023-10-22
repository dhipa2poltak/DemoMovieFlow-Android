package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.VoidResult
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class DeleteFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): DeleteFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult> {
    return appRepository.deleteFavoriteMovie(movie)
  }
}
