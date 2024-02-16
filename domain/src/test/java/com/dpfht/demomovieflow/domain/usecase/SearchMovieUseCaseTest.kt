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
class SearchMovieUseCaseTest: BaseUseCaseTest() {

  private lateinit var searchMovieUseCase: SearchMovieUseCase

  private val page = 1
  private val key = "lope lope"
  private val movieDomain = MovieDomain(page, listOf(), 10, 10)

  @Before
  fun setup() {
    searchMovieUseCase = SearchMovieUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure searchMovie method is called in AppRepository class`() = runTest {
    searchMovieUseCase(key, page).collect {}

    verify(appRepository).searchMovie(key, page)
  }

  @Test
  fun `search movie successfully`() = runTest {
    whenever(appRepository.searchMovie(key, page)).thenReturn(movieDomain)

    val expected = Result.Success(movieDomain)
    var actual: Result<MovieDomain>? = null
    searchMovieUseCase(key, page).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail search movie`() = runTest {
    val msg = "error search movie"

    whenever(appRepository.searchMovie(key, page)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<MovieDomain>? = null
    searchMovieUseCase(key, page).collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
