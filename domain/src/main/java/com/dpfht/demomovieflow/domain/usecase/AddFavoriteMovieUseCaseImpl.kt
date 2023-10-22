package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AddFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): AddFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>> {
    return appRepository.addFavoriteMovie(movie)
  }
}
