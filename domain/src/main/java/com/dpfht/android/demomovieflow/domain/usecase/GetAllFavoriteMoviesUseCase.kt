package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteMoviesUseCase {

  suspend operator fun invoke(): Flow<Result<List<FavoriteMovieDBEntity>>>
}
