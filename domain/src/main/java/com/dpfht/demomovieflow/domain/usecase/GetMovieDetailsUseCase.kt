package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>>
}
