package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.MovieDomain
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class SearchMovieUseCaseImpl(
  private val appRepository: AppRepository
): SearchMovieUseCase {

  override suspend operator fun invoke(query: String, page: Int): Flow<Result<MovieDomain>> {
    return appRepository.searchMovie(query, page)
  }
}
