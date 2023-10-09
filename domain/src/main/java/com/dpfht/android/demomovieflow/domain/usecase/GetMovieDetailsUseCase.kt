package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>>
}
