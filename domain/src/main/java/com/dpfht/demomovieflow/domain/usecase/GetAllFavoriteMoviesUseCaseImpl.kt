package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllFavoriteMoviesUseCaseImpl(
  private val appRepository: AppRepository
): GetAllFavoriteMoviesUseCase {

  override suspend operator fun invoke(): Flow<Result<List<FavoriteMovieDBEntity>>> = flow {
    try {
      emit(Result.Success(appRepository.getAllFavoriteMovies()))
    } catch (e: AppException) {
      emit(Result.Error(e.message))
    }
  }
}
