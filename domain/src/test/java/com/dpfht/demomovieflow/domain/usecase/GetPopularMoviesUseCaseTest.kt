package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieDomain
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
class GetPopularMoviesUseCaseTest: BaseUseCaseTest() {

  private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

  private val page = 1
  private val movieDomain = MovieDomain(page, listOf(), 10, 10)

  @Before
  fun setup() {
    getPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getPopularMovies method is called in AppRepository class`() = runTest {
    getPopularMoviesUseCase(page).collect {}

    verify(appRepository).getPopularMovies(page)
  }

  @Test
  fun `get popular movies successfully`() = runTest {
    whenever(appRepository.getPopularMovies(page)).thenReturn(movieDomain)

    val expected = Result.Success(movieDomain)
    var actual: Result<MovieDomain>? = null
    getPopularMoviesUseCase(page).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail get popular movies`() = runTest {
    val msg = "error get popular movies"

    whenever(appRepository.getPopularMovies(page)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<MovieDomain>? = null
    getPopularMoviesUseCase(page).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
