package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMovieUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>>
}
