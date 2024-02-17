package com.dpfht.android.demomovieflow.framework.data.datasource.remote.rest

import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RestServiceTest {

  private lateinit var mockWebServer: MockWebServer
  private lateinit var restService: RestService
  private lateinit var gson: Gson

  private val page = 1
  private val key = "lope lope"
  private val movieId = 101

  @Before
  fun setup() {
    gson = Gson()
    mockWebServer = MockWebServer()

    restService = Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
      .build().create(RestService::class.java)

    val mockResponse = MockResponse()
    mockWebServer.enqueue(mockResponse.setBody("{}"))
  }

  @Test
  fun `ensure path and parameter(s) in the generated URL are correct when calling getPopularMovies method in RestService`() = runTest {
    restService.getPopularMovies(page)
    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/3/movie/popular", uri.path)
    assertTrue(uri.queryParameterNames.contains("page"))
    assertTrue(uri.getQueryParameter("page") == "$page")
  }

  @Test
  fun `ensure path and parameter(s) in the generated URL are correct when calling searchMovie method in RestService`() = runTest {
    restService.searchMovie(key, page)
    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/3/search/movie", uri.path)
    assertTrue(uri.queryParameterNames.contains("query"))
    assertTrue(uri.getQueryParameter("query") == key)
    assertTrue(uri.queryParameterNames.contains("page"))
    assertTrue(uri.getQueryParameter("page") == "$page")
  }

  @Test
  fun `ensure path in the generated URL is correct when calling getMovieDetails method in RestService`() = runTest {
    restService.getMovieDetails(movieId)
    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/3/movie/${movieId}", uri.path)
  }
}
