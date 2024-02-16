package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): GetFavoriteMovieUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>> = flow {
    try {
      emit(Result.Success(appRepository.getFavoriteMovie(movieId)))
    } catch (e: AppException) {
      emit(Result.Error(e.message))
    }
  }
}
