package com.dpfht.android.demomovieflow.feature_favorite_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.android.demomovieflow.feature_favorite_movies.adapter.FavoriteMoviesAdapter
import com.dpfht.android.demomovieflow.framework.commons.model.FavoriteMovieVWModel
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.GetAllFavoriteMoviesUseCase
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMoviesViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: FavoriteMoviesViewModel

  @Mock
  private lateinit var adapter: FavoriteMoviesAdapter

  @Mock
  private lateinit var getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase

  @Mock
  private lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  private val cacheModels = arrayListOf<FavoriteMovieVWModel>()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = FavoriteMoviesViewModel(
      getAllFavoriteMoviesUseCase,
      deleteFavoriteMovieUseCase,
      cacheModels,
      adapter
    )
  }

  @Test
  fun `fetch favorite movies successfully`() = runTest {
    val movie1 = FavoriteMovieDBEntity(1, 101)
    val movie2 = FavoriteMovieDBEntity(2, 102)
    val movie3 = FavoriteMovieDBEntity(1, 103)
    val models = listOf(movie1, movie2, movie3)

    val retFlow = flow {
      emit(Result.Success(models))
    }

    whenever(getAllFavoriteMoviesUseCase.invoke()).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    assertTrue(cacheModels.size == models.size)
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch favorite movies`() = runTest {
    val msg = "error fetch genre"

    val retFlow = flow {
      emit(Result.Error(msg))
    }

    whenever(getAllFavoriteMoviesUseCase.invoke()).thenReturn(retFlow)

    viewModel.modalMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }
}
