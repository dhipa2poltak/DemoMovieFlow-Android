package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.MovieDomain
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCaseImpl(
  private val appRepository: AppRepository
): GetPopularMoviesUseCase {

  override suspend operator fun invoke(page: Int): Flow<Result<MovieDomain>> {
    return appRepository.getPopularMovies(page)
  }
}
