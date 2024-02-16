package com.dpfht.android.demomovieflow.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.demomovieflow.framework.data.datasource.remote.rest.RestService
import com.dpfht.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.demomovieflow.data.model.ApiErrorResponse
import com.dpfht.demomovieflow.domain.entity.AppException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

  private lateinit var remoteDataSource: RemoteDataSource

  @Mock
  private lateinit var restService: RestService

  @Mock
  private lateinit var context: Context

  private val page = 1
  private val movieId = 101
  private val key = "lope lope"

  @Before
  fun setup() {
    remoteDataSource = RemoteDataSourceImpl(context, restService)
  }

  @Test
  fun `ensure getPopularMovies method is called in RestService`() = runTest {
    try {
      remoteDataSource.getPopularMovies(page)
    } catch (_: Exception) {}

    verify(restService).getPopularMovies(page)
  }

  @Test
  fun `ensure getMovieDetails method is called in RestService`() = runTest {
    try {
      remoteDataSource.getMovieDetails(movieId)
    } catch (_: Exception) {}

    verify(restService).getMovieDetails(movieId)
  }

  @Test
  fun `ensure searchMovie method is called in RestService`() = runTest {
    try {
      remoteDataSource.searchMovie(key, page)
    } catch (_: Exception) {}

    verify(restService).searchMovie(key, page)
  }

  @Test
  fun `failed when hitting API but ApiErrorResponse is available`() = runTest {
    val msg = "The resource you requested could not be found."
    val strBody = """
        {"success":false,"status_code":34,"status_message":"$msg"}
      """.trimIndent()

    whenever(restService.getPopularMovies(page)).then {
      val body = strBody.toResponseBody("application/json".toMediaTypeOrNull())
      val r = Response.error<ApiErrorResponse>(400, body)
      throw HttpException(r)
    }

    try {
      remoteDataSource.getPopularMovies(page)
    } catch (e: AppException) {
      assertTrue(msg == e.message)
    }
  }

  @Test
  fun `failed when hitting API and ApiErrorResponse is not available`() = runTest {
    val message = "this is a test"
    `when`(context.getString(Mockito.anyInt())).thenReturn(message)

    whenever(restService.getPopularMovies(page)).then {
      val body = "".toResponseBody("application/json".toMediaTypeOrNull())
      val r = Response.error<ApiErrorResponse>(400, body)
      throw HttpException(r)
    }

    try {
      remoteDataSource.getPopularMovies(page)
    } catch (e: AppException) {
      assertTrue(message == e.message)
    }
  }
}
