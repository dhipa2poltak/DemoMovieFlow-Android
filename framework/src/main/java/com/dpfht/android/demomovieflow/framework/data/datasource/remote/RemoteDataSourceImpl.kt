package com.dpfht.android.demomovieflow.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.demomovieflow.framework.R
import com.dpfht.android.demomovieflow.framework.data.datasource.remote.rest.RestService
import com.dpfht.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.demomovieflow.data.model.ApiErrorResponse
import com.dpfht.demomovieflow.data.model.toDomain
import com.dpfht.demomovieflow.domain.entity.AppException
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getPopularMovies(page: Int): MovieDomain {
    return safeApiCall(Dispatchers.IO) { restService.getPopularMovies(page) }.toDomain()
  }

  override suspend fun getMovieDetails(movieId: Int): MovieEntity {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetails(movieId) }.toDomain()
  }

  override suspend fun searchMovie(query: String, page: Int): MovieDomain {
    return safeApiCall(Dispatchers.IO) { restService.searchMovie(query, page) }.toDomain()
  }

  //--

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> AppException(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            val errorResponse = convertErrorBody(t)

            AppException(errorResponse?.statusMessage ?: context.getString(R.string.framework_text_error_http))
          }
          else -> {
            AppException(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ApiErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ApiErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ApiErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
