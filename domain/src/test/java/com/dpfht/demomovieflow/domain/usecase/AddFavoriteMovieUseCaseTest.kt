package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.entity.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddFavoriteMovieUseCaseTest: BaseUseCaseTest() {

  private lateinit var addFavoriteMovieUseCase: AddFavoriteMovieUseCase

  private val movieEntity = MovieEntity(10, "Title Movie", "Overview Movie", "image movie", listOf())
  private val favMovieDB = FavoriteMovieDBEntity(1, 10)

  @Before
  fun setup() {
    addFavoriteMovieUseCase = AddFavoriteMovieUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure addFavoriteMovie method is called in AppRepository class`() = runTest {
    addFavoriteMovieUseCase(movieEntity).collect {}

    verify(appRepository).addFavoriteMovie(movieEntity)
  }

  @Test
  fun `add favorite movie successfully`() = runTest {
    whenever(appRepository.addFavoriteMovie(movieEntity)).thenReturn(favMovieDB)

    val expected = Result.Success(favMovieDB)
    var actual: Result<FavoriteMovieDBEntity>? = null
    addFavoriteMovieUseCase(movieEntity).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail add favorite movie`() = runTest {
    val msg = "error add favorite movie"

    whenever(appRepository.addFavoriteMovie(movieEntity)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<FavoriteMovieDBEntity>? = null
    addFavoriteMovieUseCase(movieEntity).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
