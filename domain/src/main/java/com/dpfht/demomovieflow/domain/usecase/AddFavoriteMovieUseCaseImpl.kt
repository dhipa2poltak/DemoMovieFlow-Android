package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): AddFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>> = flow {
    try {
      emit(Result.Success(appRepository.addFavoriteMovie(movie)))
    } catch (e: AppException) {
      emit(Result.Error(e.message))
    }
  }
}
