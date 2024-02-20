package com.dpfht.android.demomovieflow.feature_search_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingSource.LoadParams
import com.dpfht.android.demomovieflow.feature_search_movie.paging.SearchMovieDataSource
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.Result.Success
import com.dpfht.demomovieflow.domain.usecase.SearchMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SearchMovieViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: SearchMovieViewModel

  private lateinit var searchMovieDataSource: SearchMovieDataSource

  private lateinit var searchMovieUseCase: SearchMovieUseCase
  private lateinit var errorMessageObserver: Observer<String>
  private lateinit var noDataObserver: Observer<Boolean>
  private lateinit var setAdapterObserver: Observer<Boolean>

  private val page = 1
  private val key = "title"

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    searchMovieUseCase = mock()
    errorMessageObserver = mock()
    noDataObserver = mock()
    setAdapterObserver = mock()

    searchMovieDataSource = SearchMovieDataSource(searchMovieUseCase)

    viewModel = SearchMovieViewModel(searchMovieDataSource)
  }

  @Test
  fun `search movie successfully`() = runTest {
    val movie1 = MovieEntity(id = 1, title = "title1", overview = "overview1")
    val movie2 = MovieEntity(id = 2, title = "title2", overview = "overview2")
    val movie3 = MovieEntity(id = 3, title = "title3", overview = "overview3")

    val movies = listOf(movie1, movie2, movie3)
    val movieDomain = MovieDomain(page, movies)

    val retFlow = flow {
      emit(Success(movieDomain))
    }

    whenever(searchMovieUseCase.invoke(key, page)).thenReturn(retFlow)

    viewModel.setAdapterData.observeForever(setAdapterObserver)

    viewModel.onSearchMovie(key)
    searchMovieDataSource.load(LoadParams.Refresh(1, 20, false, 20))

    verify(setAdapterObserver).onChanged(eq(true))
    assertTrue(viewModel.adapter.itemCount == movies.size)
  }

  @Test
  fun `search movie successfully but no data available`() = runTest {
    val movies = listOf<MovieEntity>()
    val movieDomain = MovieDomain(page, movies)

    val retFlow = flow {
      emit(Success(movieDomain))
    }

    whenever(searchMovieUseCase.invoke(key, page)).thenReturn(retFlow)

    viewModel.isNoData.observeForever(noDataObserver)
    viewModel.setAdapterData.observeForever(setAdapterObserver)

    viewModel.onSearchMovie(key)

    verify(setAdapterObserver).onChanged(eq(true))
    verify(noDataObserver).onChanged(eq(true))
  }

  @Test
  fun `failed search movie`() = runTest {
    val msg = "error search movie"

    val retFlow = flow {
      emit(Result.Error(msg))
    }

    val page = 1

    whenever(searchMovieUseCase.invoke(key, page)).thenReturn(retFlow)

    viewModel.modalMessage.observeForever(errorMessageObserver)
    viewModel.setAdapterData.observeForever(setAdapterObserver)

    viewModel.onSearchMovie(key)

    verify(setAdapterObserver).onChanged(eq(true))
    verify(errorMessageObserver).onChanged(eq(msg))
  }
}
