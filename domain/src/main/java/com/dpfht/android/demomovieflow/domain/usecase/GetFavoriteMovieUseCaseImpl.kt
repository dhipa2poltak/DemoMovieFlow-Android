package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): GetFavoriteMovieUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>> {
    return appRepository.getFavoriteMovie(movieId)
  }
}
