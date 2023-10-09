package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteMoviesUseCaseImpl(
  private val appRepository: AppRepository
): GetAllFavoriteMoviesUseCase {

  override suspend operator fun invoke(): Flow<Result<List<FavoriteMovieDBEntity>>> {
    return appRepository.getAllFavoriteMovies()
  }
}
