package com.dpfht.android.demomovieflow.domain.usecase

import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import kotlinx.coroutines.flow.Flow

interface DeleteFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult>
}
