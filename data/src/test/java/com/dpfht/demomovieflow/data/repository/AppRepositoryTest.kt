package com.dpfht.demomovieflow.data.repository

import com.dpfht.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryTest {

  private lateinit var appRepository: AppRepository

  @Mock
  private lateinit var localDataSource: LocalDataSource

  @Mock
  private lateinit var remoteDataSource: RemoteDataSource

  private val page = 1
  private val movieId = 101
  private val key = "lope lope"
  private val movieEntity = MovieEntity(movieId, "Title Movie", "Overview Movie", "image movie", listOf())

  @Before
  fun setup() {
    appRepository = AppRepositoryImpl(localDataSource, remoteDataSource)
  }

  @Test
  fun `ensure getPopularMovies method is called in RemoteDataSource class`() = runTest {
    appRepository.getPopularMovies(page)

    verify(remoteDataSource).getPopularMovies(page)
  }

  @Test
  fun `ensure getMovieDetails method is called in RemoteDataSource class`() = runTest {
    appRepository.getMovieDetails(movieId)

    verify(remoteDataSource).getMovieDetails(movieId)
  }

  @Test
  fun `ensure searchMovie method is called in RemoteDataSource class`() = runTest {
    appRepository.searchMovie(key, page)

    verify(remoteDataSource).searchMovie(key, page)
  }

  @Test
  fun `ensure getAllFavoriteMovies method is called in LocalDataSource class`() = runTest {
    appRepository.getAllFavoriteMovies()

    verify(localDataSource).getAllFavoriteMovies()
  }

  @Test
  fun `ensure getFavoriteMovie method is called in LocalDataSource class`() = runTest {
    appRepository.getFavoriteMovie(movieId)

    verify(localDataSource).getFavoriteMovie(movieId)
  }

  @Test
  fun `ensure addFavoriteMovie method is called in LocalDataSource class`() = runTest {
    appRepository.addFavoriteMovie(movieEntity)

    verify(localDataSource).addFavoriteMovie(movieEntity)
  }

  @Test
  fun `ensure deleteFavoriteMovie method is called in LocalDataSource class`() = runTest {
    appRepository.deleteFavoriteMovie(movieEntity)

    verify(localDataSource).deleteFavoriteMovie(movieEntity)
  }
}
