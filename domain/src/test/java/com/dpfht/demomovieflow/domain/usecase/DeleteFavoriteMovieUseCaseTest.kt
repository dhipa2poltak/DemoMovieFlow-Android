package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.VoidResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DeleteFavoriteMovieUseCaseTest: BaseUseCaseTest() {

  private lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

  private val movieEntity = MovieEntity(10, "Title Movie", "Overview Movie", "image movie", listOf())

  @Before
  fun setup() {
    deleteFavoriteMovieUseCase = DeleteFavoriteMovieUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure deleteFavoriteMovie method is called in AppRepository class`() = runTest {
    deleteFavoriteMovieUseCase(movieEntity).collect {}

    verify(appRepository).deleteFavoriteMovie(movieEntity)
  }

  @Test
  fun `delete favorite movie successfully`() = runTest {
    whenever(appRepository.deleteFavoriteMovie(movieEntity)).then {}

    val expected = VoidResult.Success
    var actual: VoidResult? = null
    deleteFavoriteMovieUseCase(movieEntity).collect {
      actual = it
    }

    Assert.assertTrue(actual != null)
    Assert.assertTrue(expected == actual)
  }

  @Test
  fun `fail delete favorite movie`() = runTest {
    val msg = "error delete favorite movie"

    whenever(appRepository.deleteFavoriteMovie(movieEntity)).then {
      throw AppException(msg)
    }

    val expected = VoidResult.Error(msg)
    var actual: VoidResult? = null
    deleteFavoriteMovieUseCase(movieEntity).collect {
      actual = it
    }

    Assert.assertTrue(actual != null)
    Assert.assertTrue(expected == actual)
  }
}
