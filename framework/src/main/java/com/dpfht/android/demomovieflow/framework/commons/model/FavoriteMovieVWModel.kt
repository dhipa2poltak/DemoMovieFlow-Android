package com.dpfht.android.demomovieflow.framework.commons.model

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity

data class FavoriteMovieVWModel(
  val favEntity: FavoriteMovieDBEntity,
  var movieEntity: MovieEntity? = null
)
