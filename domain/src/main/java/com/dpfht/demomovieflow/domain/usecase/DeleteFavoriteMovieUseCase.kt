package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.VoidResult
import kotlinx.coroutines.flow.Flow

interface DeleteFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult>
}
