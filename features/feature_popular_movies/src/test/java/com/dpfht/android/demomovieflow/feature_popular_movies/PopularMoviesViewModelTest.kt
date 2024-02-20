package com.dpfht.android.demomovieflow.feature_popular_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingSource.LoadParams
import com.dpfht.android.demomovieflow.feature_popular_movies.paging.PopularMoviesDataSource
import com.dpfht.android.demomovieflow.framework.commons.adapter.MovieAdapter
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PopularMoviesViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: PopularMoviesViewModel

  private lateinit var popularMoviesDataSource: PopularMoviesDataSource

  @Mock
  private lateinit var adapter: MovieAdapter

  @Mock
  private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Mock
  private lateinit var noDataObserver: Observer<Boolean>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    popularMoviesDataSource = PopularMoviesDataSource(getPopularMoviesUseCase)

    viewModel = PopularMoviesViewModel(popularMoviesDataSource, adapter)
  }

  @Test
  fun `fetch popular movies successfully`() = runTest {
    val movie1 = MovieEntity(id = 1, title = "title1", overview = "overview1")
    val movie2 = MovieEntity(id = 2, title = "title2", overview = "overview2")
    val movie3 = MovieEntity(id = 3, title = "title3", overview = "overview3")

    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val movieDomain = MovieDomain(page, movies)

    val retFlow = flow {
      emit(Result.Success(movieDomain))
    }

    whenever(getPopularMoviesUseCase.invoke(page)).thenReturn(retFlow)
    whenever(adapter.itemCount).thenReturn(0)

    viewModel.start()
    popularMoviesDataSource.load(LoadParams.Refresh(1, 20, false, 20))

    verify(adapter).submitData(any())

    whenever(adapter.itemCount).thenReturn(movies.size)
    viewModel.start()
    verify(adapter, times(2)).itemCount
  }

  @Test
  fun `fetch popular movies successfully but no data available`() = runTest {
    val page = 1

    val movies = listOf<MovieEntity>()
    val movieDomain = MovieDomain(page, movies)

    val retFlow = flow {
      emit(Result.Success(movieDomain))
    }

    whenever(getPopularMoviesUseCase.invoke(page)).thenReturn(retFlow)
    whenever(adapter.itemCount).thenReturn(0)

    viewModel.isNoData.observeForever(noDataObserver)

    viewModel.start()
    popularMoviesDataSource.load(LoadParams.Refresh(1, 20, false, 20))

    verify(noDataObserver).onChanged(eq(true))
  }

  @Test
  fun `failed fetch popular movies`() = runTest {
    val msg = "error fetch movie"

    val retFlow = flow {
      emit(Result.Error(msg))
    }

    val page = 1

    whenever(getPopularMoviesUseCase.invoke(page)).thenReturn(retFlow)
    whenever(adapter.itemCount).thenReturn(0)

    viewModel.modalMessage.observeForever(errorMessageObserver)

    viewModel.start()
    popularMoviesDataSource.load(LoadParams.Refresh(1, 20, false, 20))

    verify(errorMessageObserver).onChanged(eq(msg))
  }
}
