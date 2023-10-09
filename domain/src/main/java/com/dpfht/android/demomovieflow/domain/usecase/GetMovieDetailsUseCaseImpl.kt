package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>> {
    return appRepository.getMovieDetails(movieId)
  }
}
