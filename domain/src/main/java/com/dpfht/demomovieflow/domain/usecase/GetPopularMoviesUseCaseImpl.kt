package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCaseImpl(
  private val appRepository: AppRepository
): GetPopularMoviesUseCase {

  override suspend operator fun invoke(page: Int): Flow<Result<MovieDomain>> {
    return appRepository.getPopularMovies(page)
  }
}
