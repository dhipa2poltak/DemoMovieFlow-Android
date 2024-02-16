package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieEntity
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
class GetMovieDetailsUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  private val movieId = 10
  private val movieEntity = MovieEntity(movieId, "Title Movie", "Overview Movie", "image movie", listOf())

  @Before
  fun setup() {
    getMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieDetails method is called in AppRepository class`() = runTest {
    getMovieDetailsUseCase(movieId).collect {}

    verify(appRepository).getMovieDetails(movieId)
  }

  @Test
  fun `get movie details successfully`() = runTest {
    whenever(appRepository.getMovieDetails(movieId)).thenReturn(movieEntity)

    val expected = Result.Success(movieEntity)
    var actual: Result<MovieEntity>? = null
    getMovieDetailsUseCase(movieId).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail get movie details`() = runTest {
    val msg = "error get movie details"

    whenever(appRepository.getMovieDetails(movieId)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<MovieEntity>? = null
    getMovieDetailsUseCase(movieId).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
