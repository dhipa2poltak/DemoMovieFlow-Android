package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
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
class GetFavoriteMovieUseCaseTest: BaseUseCaseTest() {

  private lateinit var getFavoriteMovieUseCase: GetFavoriteMovieUseCase

  private val movieId = 10
  private val favMovieDB = FavoriteMovieDBEntity(1, movieId)

  @Before
  fun setup() {
    getFavoriteMovieUseCase = GetFavoriteMovieUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getFavoriteMovie method is called in AppRepository class`() = runTest {
    getFavoriteMovieUseCase(movieId).collect {}

    verify(appRepository).getFavoriteMovie(movieId)
  }

  @Test
  fun `get favorite movie successfully`() = runTest {
    whenever(appRepository.getFavoriteMovie(movieId)).thenReturn(favMovieDB)

    val expected = Result.Success(favMovieDB)
    var actual: Result<FavoriteMovieDBEntity?>? = null
    getFavoriteMovieUseCase(movieId).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail get favorite movie`() = runTest {
    val msg = "error get favorite movie"

    whenever(appRepository.getFavoriteMovie(movieId)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<FavoriteMovieDBEntity?>? = null
    getFavoriteMovieUseCase(movieId).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
