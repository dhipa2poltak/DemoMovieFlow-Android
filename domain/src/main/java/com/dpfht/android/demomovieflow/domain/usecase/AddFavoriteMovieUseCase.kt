package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface AddFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>>
}
