package com.dpfht.android.demomovieflow.framework.data.datasource.local

import android.content.Context
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.dao.FavoriteMovieDao
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.model.FavoriteMovieDBModel
import com.dpfht.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceTest {

  private lateinit var localDataSource: LocalDataSource

  @Mock
  private lateinit var context: Context

  @Mock
  private lateinit var appDB: AppDB

  @Mock
  private lateinit var favMovieDao: FavoriteMovieDao

  private val movieId = 101
  private val movieEntity = MovieEntity(movieId, "movie title", "movie overview", "movie image", listOf())

  private val msg = "this is error message"

  @Before
  fun setup() {
    localDataSource = LocalDataSourceImpl(context, appDB)

    whenever(appDB.favoriteMovieDao()).thenReturn(favMovieDao)
    whenever(context.getString(anyInt())).thenReturn(msg)
  }

  @Test
  fun `ensure getAllFavoriteMovies method is called in FavoriteMovieDao`() = runTest {
    try {
      localDataSource.getAllFavoriteMovies()
    } catch (_: Exception) {}

    verify(favMovieDao).getAllFavoriteMovies()
  }

  @Test
  fun `ensure the AppException is thrown when calling getAllFavoriteMovies method in LocalDataSource generates exception`() = runTest {
    whenever(favMovieDao.getAllFavoriteMovies()).then {
      throw Exception()
    }

    try {
      localDataSource.getAllFavoriteMovies()
    } catch (e: AppException) {
      assertTrue(e.message == msg)
    }
  }

  @Test
  fun `ensure getFavoriteMovie method is called in FavoriteMovieDao`() = runTest {
    try {
      localDataSource.getFavoriteMovie(movieId)
    } catch (_: Exception) {}

    verify(favMovieDao).getFavoriteMovie(movieId)
  }

  @Test
  fun `ensure the AppException is thrown when calling getFavoriteMovie method in LocalDataSource generates exception`() = runTest {
    whenever(favMovieDao.getFavoriteMovie(movieId)).then {
      throw Exception()
    }

    try {
      localDataSource.getFavoriteMovie(movieId)
    } catch (e: AppException) {
      assertTrue(e.message == msg)
    }
  }

  @Test
  fun `ensure insertFavoriteMovie method is called in FavoriteMovieDao`() = runTest {
    try {
      localDataSource.addFavoriteMovie(movieEntity)
    } catch (_: Exception) {}

    verify(favMovieDao).insertFavoriteMovie(FavoriteMovieDBModel(movieId = movieEntity.id))
  }

  @Test
  fun `ensure the AppException is thrown when calling addFavoriteMovie method in LocalDataSource generates exception`() = runTest {
    val dbModel = FavoriteMovieDBModel(movieId = movieId)
    whenever(favMovieDao.insertFavoriteMovie(dbModel)).then {
      throw Exception()
    }

    try {
      localDataSource.addFavoriteMovie(movieEntity)
    } catch (e: AppException) {
      assertTrue(e.message == msg)
    }
  }

  @Test
  fun `ensure deleteFavoriteMovie method is called in FavoriteMovieDao`() = runTest {
    try {
      localDataSource.deleteFavoriteMovie(movieEntity)
    } catch (_: Exception) {}

    verify(favMovieDao).deleteFavoriteMovie(movieId)
  }

  @Test
  fun `ensure the AppException is thrown when calling deleteFavoriteMovie method in LocalDataSource generates exception`() = runTest {
    whenever(favMovieDao.deleteFavoriteMovie(movieId)).then {
      throw Exception()
    }

    try {
      localDataSource.deleteFavoriteMovie(movieEntity)
    } catch (e: AppException) {
      assertTrue(e.message == msg)
    }
  }
}
