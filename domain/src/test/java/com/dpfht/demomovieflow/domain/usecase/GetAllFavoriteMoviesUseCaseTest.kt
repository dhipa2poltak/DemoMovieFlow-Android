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
class GetAllFavoriteMoviesUseCaseTest: BaseUseCaseTest() {

  private lateinit var getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase

  private val favMovieDB1 = FavoriteMovieDBEntity(1, 10)
  private val favMovieDB2 = FavoriteMovieDBEntity(2, 20)
  private val favMovieDB3 = FavoriteMovieDBEntity(3, 30)
  private val listFavMovieDbs = listOf(favMovieDB1, favMovieDB2, favMovieDB3)

  @Before
  fun setup() {
    getAllFavoriteMoviesUseCase = GetAllFavoriteMoviesUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getAllFavoriteMovies method is called in AppRepository class`() = runTest {
    getAllFavoriteMoviesUseCase().collect {}

    verify(appRepository).getAllFavoriteMovies()
  }

  @Test
  fun `get all favorite movie successfully`() = runTest {
    whenever(appRepository.getAllFavoriteMovies()).thenReturn(listFavMovieDbs)

    val expected = Result.Success(listFavMovieDbs)
    var actual: Result<List<FavoriteMovieDBEntity>>? = null
    getAllFavoriteMoviesUseCase().collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `fail get all favorite movie`() = runTest {
    val msg = "error get all favorite movie"

    whenever(appRepository.getAllFavoriteMovies()).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    var actual: Result<List<FavoriteMovieDBEntity>>? = null
    getAllFavoriteMoviesUseCase().collect {
      actual = it
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
