package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMovieUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>>
}
