package com.dpfht.android.demomovieflow.feature_movie_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.demomovieflow.domain.entity.GenreEntity
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.VoidResult
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.usecase.AddFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.GetFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.GetMovieDetailsUseCase
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
class MovieDetailsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MovieDetailsViewModel

  @Mock
  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  @Mock
  private lateinit var getFavoriteMovieUseCase: GetFavoriteMovieUseCase

  @Mock
  private lateinit var addFavoriteMovieUseCase: AddFavoriteMovieUseCase

  @Mock
  private lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

  @Mock
  private lateinit var titleObserver: Observer<String>

  @Mock
  private lateinit var overviewObserver: Observer<String>

  @Mock
  private lateinit var imageUrlObserver: Observer<String>

  @Mock
  private lateinit var showLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var isFavoriteMovieObserver: Observer<Boolean>

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Mock
  private lateinit var genresObserver: Observer<String>

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

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieDetailsViewModel(
      getMovieDetailsUseCase,
      getFavoriteMovieUseCase,
      addFavoriteMovieUseCase,
      deleteFavoriteMovieUseCase
    )
  }

  @Test
  fun `fetch movie details data successfully`() = runTest {
    val retFlow1 = flow {
      emit(Result.Success(movieEntity))
    }

    val retFlow2 = flow {
      emit(Result.Success(favMovie))
    }

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(retFlow1)
    whenever(getFavoriteMovieUseCase.invoke(movieId)).thenReturn(retFlow2)

    viewModel.titleData.observeForever(titleObserver)
    viewModel.overviewData.observeForever(overviewObserver)
    viewModel.imageUrlData.observeForever(imageUrlObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.genres.observeForever(genresObserver)
    viewModel.isFavoriteData.observeForever(isFavoriteMovieObserver)

    viewModel.movieId = movieId
    viewModel.start()

    verify(titleObserver).onChanged(eq(title))
    verify(overviewObserver).onChanged(eq(overview))
    verify(imageUrlObserver).onChanged(eq(posterPath))
    verify(showLoadingObserver).onChanged(eq(false))
    verify(genresObserver).onChanged("$genreName1, $genreName2")
    verify(isFavoriteMovieObserver).onChanged(eq(true))

    assertTrue(viewModel.movieId == movieId)
    assertTrue(viewModel.titleData.value == title)
    assertTrue(viewModel.overviewData.value == overview)
    assertTrue(viewModel.imageUrlData.value == posterPath)

    viewModel.start()

    assertTrue(viewModel.titleData.value == title)
    assertTrue(viewModel.overviewData.value == overview)
    assertTrue(viewModel.imageUrlData.value == posterPath)
  }

  @Test
  fun `fetch movie details data successfully but error in checking is favorite movie`() = runTest {
    val msg = "error checking is favorite movie"

    val retFlow1 = flow {
      emit(Result.Success(movieEntity))
    }

    val retFlow2 = flow {
      emit(Result.Error(msg))
    }

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(retFlow1)
    whenever(getFavoriteMovieUseCase.invoke(movieId)).thenReturn(retFlow2)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.modalMessage.observeForever(errorMessageObserver)

    viewModel.movieId = movieId
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(errorMessageObserver).onChanged(eq(""))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `failed fetch movie details`() = runTest {
    val msg = "error fetch movie details"

    val retFlow = flow {
      emit(Result.Error(msg))
    }

    val movieId = 1

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(retFlow)

    viewModel.modalMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)

    viewModel.movieId = movieId
    viewModel.start()

    verify(errorMessageObserver).onChanged(eq(msg))
    verify(showLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `add favorite movie successfully`() = runTest {
    viewModel.movieEntity = movieEntity

    val retFlow = flow {
      emit(Result.Success(favMovie))
    }

    whenever(addFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.isFavoriteData.observeForever(isFavoriteMovieObserver)

    viewModel.addFavoriteMovie()

    verify(showLoadingObserver).onChanged(eq(false))
    verify(isFavoriteMovieObserver).onChanged(eq(true))
  }

  @Test
  fun `add favorite movie for result`() = runTest {
    viewModel.movieEntity = movieEntity
    viewModel.isForResult = true

    viewModel.isFavoriteData.observeForever(isFavoriteMovieObserver)

    viewModel.addFavoriteMovie()

    verify(isFavoriteMovieObserver).onChanged(eq(true))
  }

  @Test
  fun `failed add favorite movie`() = runTest {
    viewModel.movieEntity = movieEntity

    val msg = "error add favorite movie"

    val retFlow = flow {
      emit(Result.Error(msg))
    }

    whenever(addFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.modalMessage.observeForever(errorMessageObserver)

    viewModel.addFavoriteMovie()

    verify(showLoadingObserver).onChanged(eq(false))
    verify(errorMessageObserver).onChanged(eq(msg))
  }

  @Test
  fun `delete favorite movie successfully`() = runTest {
    viewModel.movieEntity = movieEntity

    val retFlow = flow {
      emit(VoidResult.Success)
    }

    whenever(deleteFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.isFavoriteData.observeForever(isFavoriteMovieObserver)

    viewModel.deleteFavoriteMovie()

    verify(showLoadingObserver).onChanged(eq(false))
    verify(isFavoriteMovieObserver).onChanged(eq(false))
  }

  @Test
  fun `delete favorite movie for result`() = runTest {
    viewModel.movieEntity = movieEntity
    viewModel.isForResult = true

    viewModel.isFavoriteData.observeForever(isFavoriteMovieObserver)

    viewModel.deleteFavoriteMovie()

    verify(isFavoriteMovieObserver).onChanged(eq(false))
  }

  @Test
  fun `failed delete favorite movie`() = runTest {
    val msg = "error delete favorite movie"

    viewModel.movieEntity = movieEntity

    val retFlow = flow {
      emit(VoidResult.Error(msg))
    }

    whenever(deleteFavoriteMovieUseCase(movieEntity)).thenReturn(retFlow)

    viewModel.isShowDialogLoading.observeForever(showLoadingObserver)
    viewModel.modalMessage.observeForever(errorMessageObserver)

    viewModel.deleteFavoriteMovie()

    verify(showLoadingObserver).onChanged(eq(false))
    verify(errorMessageObserver).onChanged(eq(msg))
  }
}
