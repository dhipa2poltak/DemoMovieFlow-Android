package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.MovieDomain
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {

  suspend operator fun invoke(page: Int): Flow<Result<MovieDomain>>
}
