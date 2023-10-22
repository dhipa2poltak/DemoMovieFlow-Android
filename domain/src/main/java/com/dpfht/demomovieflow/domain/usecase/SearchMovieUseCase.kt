package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import kotlinx.coroutines.flow.Flow

interface SearchMovieUseCase {

  suspend operator fun invoke(query: String, page: Int): Flow<Result<MovieDomain>>
}
