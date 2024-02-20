package com.dpfht.android.demomovieflow.feature_favorite_movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.android.demomovieflow.feature_favorite_movies.adapter.FavoriteMoviesAdapter
import com.dpfht.android.demomovieflow.framework.commons.model.FavoriteMovieVWModel
import com.dpfht.demomovieflow.domain.entity.GenreEntity
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.VoidResult
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

  private val movieId = 101
  private val title = "title1"
  private val overview = "overview1"
  private val posterPath = "poster_path1"
  private val genreName1 = "Action"
  private val genreName2 = "Drama"
  private val genres = listOf(GenreEntity(10, genreName1), GenreEntity(11, genreName2))

  private val movieEntity = MovieEntity(
    id = movieId,
    title = title,
    overview = overview,
    imageUrl = posterPath,
    genres = genres
  )

  private val favMovie = FavoriteMovieDBEntity(1, movieId)
  private val favMovieModel = FavoriteMovieVWModel(favMovie, movieEntity)

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

  @Test
  fun `delete favorite movie from details screen successfully`() = runTest {
    viewModel.isFromDetails = true
    viewModel.isRemovingMovie = true
    viewModel.movieIdToRemove = 101

    cacheModels.add(favMovieModel)

    val retFlow = flow {
      emit(VoidResult.Success)
    }

    whenever(deleteFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.start()

    verify(showLoadingObserver).onChanged(eq(false))
    assertTrue(cacheModels.isEmpty())
  }

  @Test
  fun `failed delete favorite movie from details screen`() = runTest {
    val msg = "error delete favorite movie"

    viewModel.isFromDetails = true
    viewModel.isRemovingMovie = true
    viewModel.movieIdToRemove = 101

    cacheModels.add(favMovieModel)

    val retFlow = flow {
      emit(VoidResult.Error(msg))
    }

    whenever(deleteFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.modalMessage.observeForever(errorMessageObserver)

    viewModel.start()

    verify(showLoadingObserver).onChanged(eq(false))
    verify(errorMessageObserver).onChanged(eq(msg))
  }
}
