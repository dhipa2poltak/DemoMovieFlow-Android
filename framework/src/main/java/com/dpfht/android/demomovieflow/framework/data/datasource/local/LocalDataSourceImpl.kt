package com.dpfht.android.demomovieflow.framework.data.datasource.local

import android.content.Context
import com.dpfht.android.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.framework.R
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.model.FavoriteMovieDBModel
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
  private val context: Context,
  private val appDB: AppDB
): LocalDataSource {

  override suspend fun getAllFavoriteMovies(): Flow<Result<List<FavoriteMovieDBEntity>>> = flow {
    try {
      val list = withContext(Dispatchers.IO) {
        appDB.favoriteMovieDao().getAllFavoriteMovies().map { it.toDomain() }
      }

      emit(Result.Success(list))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Result.ErrorResult(context.getString(R.string.framework_text_fail_get_all_favorite_movies)))
    }
  }.flowOn(Dispatchers.IO)

  override suspend fun getFavoriteMovie(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>> = flow {
    try {
      val list = withContext(Dispatchers.IO) {
        appDB.favoriteMovieDao().getFavoriteMovie(movieId)
      }

      emit(
        if (list.isNotEmpty()) {
          Result.Success(list.first().toDomain())
        } else {
          Result.Success(null)
        }
      )
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Result.ErrorResult(context.getString(R.string.framework_text_fail_get_favorite_movie)))
    }
  }.flowOn(Dispatchers.IO)

  override suspend fun addFavoriteMovie(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>> = flow {
    try {
      val newId = withContext(Dispatchers.IO) {
        val dbModel = FavoriteMovieDBModel(movieId = movie.id)
        appDB.favoriteMovieDao().insertFavoriteMovie(dbModel)
      }

      val dbEntity = FavoriteMovieDBEntity(id = newId, movieId = movie.id)
      emit(Result.Success(dbEntity))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Result.ErrorResult(context.getString(R.string.framework_text_fail_add_favorite_movie)))
    }
  }.flowOn(Dispatchers.IO)

  override suspend fun deleteFavoriteMovie(movie: MovieEntity): Flow<VoidResult> = flow {
    try {
      withContext(Dispatchers.IO) {
        appDB.favoriteMovieDao().deleteFavoriteMovie(movie.id)
      }

      emit(VoidResult.Success)
    } catch (e: Exception) {
      e.printStackTrace()
      emit(VoidResult.Error(context.getString(R.string.framework_text_fail_delete_favorite_movie)))
    }
  }.flowOn(Dispatchers.IO)
}
