package com.dpfht.android.demomovieflow.framework.data.datasource.local.room.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.demomovieflow.framework.data.datasource.local.room.model.FavoriteMovieDBModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMovieDaoTest {

  private lateinit var appDb: AppDB
  private lateinit var favMovieDao: FavoriteMovieDao

  private val movieId = 102
  private val movieDb1 = FavoriteMovieDBModel(1, 101)
  private val movieDb2 = FavoriteMovieDBModel(2, movieId)
  private val movieDb3 = FavoriteMovieDBModel(3, 103)
  private val listOfMovieDb = listOf(movieDb1, movieDb2, movieDb3)

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    appDb = AppDB.getDatabase(context, true)
    favMovieDao = appDb.favoriteMovieDao()
  }

  @After
  fun cleanup() {
    appDb.close()
  }

  @Test
  fun `ensure insertFavoriteMovie and getAllFavoriteMovies methods are called successfully`() = runTest {
    favMovieDao.insertFavoriteMovie(movieDb1)
    favMovieDao.insertFavoriteMovie(movieDb2)
    favMovieDao.insertFavoriteMovie(movieDb3)

    val expected = listOfMovieDb
    val actual = withContext(Dispatchers.IO) { favMovieDao.getAllFavoriteMovies() }

    assertTrue(expected == actual)
  }

  @Test
  fun `ensure insertFavoriteMovie and getFavoriteMovie methods are called successfully`() = runTest {
    favMovieDao.insertFavoriteMovie(movieDb1)
    favMovieDao.insertFavoriteMovie(movieDb2)
    favMovieDao.insertFavoriteMovie(movieDb3)

    val expected = movieDb2
    val actual = withContext(Dispatchers.IO) { favMovieDao.getFavoriteMovie(movieId).firstOrNull() }

    assertTrue(expected == actual)
  }

  @Test
  fun `ensure insertFavoriteMovie, deleteFavoriteMovie and getAllFavoriteMovies methods are called successfully`() = runTest {
    favMovieDao.insertFavoriteMovie(movieDb1)
    favMovieDao.insertFavoriteMovie(movieDb2)
    favMovieDao.insertFavoriteMovie(movieDb3)

    val list = withContext(Dispatchers.IO) {
      favMovieDao.deleteFavoriteMovie(movieId)
      favMovieDao.getAllFavoriteMovies()
    }

    val expected = 2
    val actual = list.size

    assertTrue(expected == actual)
  }
}
