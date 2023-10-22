package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface AddFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>>
}
