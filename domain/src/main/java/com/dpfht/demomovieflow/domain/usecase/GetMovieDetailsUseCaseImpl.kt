package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>> {
    return appRepository.getMovieDetails(movieId)
  }
}
