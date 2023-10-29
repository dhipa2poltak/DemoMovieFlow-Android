package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMovieUseCaseImpl(
  private val appRepository: AppRepository
): SearchMovieUseCase {

  override suspend operator fun invoke(query: String, page: Int): Flow<Result<MovieDomain>> = flow {
    try {
      emit(Result.Success(appRepository.searchMovie(query, page)))
    } catch (e: Exception) {
      e.message?.let {
        emit(Result.ErrorResult(it))
      }
    }
  }
}
