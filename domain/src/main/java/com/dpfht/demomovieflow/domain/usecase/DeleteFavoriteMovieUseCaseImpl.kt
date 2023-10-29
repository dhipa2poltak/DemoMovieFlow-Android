package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.VoidResult
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): DeleteFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult> = flow {
    try {
      appRepository.deleteFavoriteMovie(movie)

      emit(VoidResult.Success)
    } catch (e: Exception) {
      e.message?.let {
        emit(VoidResult.Error(it))
      }
    }
  }
}
