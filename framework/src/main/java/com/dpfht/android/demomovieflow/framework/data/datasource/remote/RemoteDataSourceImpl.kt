package com.dpfht.android.demomovieflow.framework.data.datasource.remote

import android.content.Context
import com.dpfht.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.demomovieflow.data.model.ErrorResponse
import com.dpfht.demomovieflow.data.model.toDomain
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.Result.ErrorResult
import com.dpfht.demomovieflow.domain.entity.Result.Success
import com.dpfht.android.demomovieflow.framework.R
import com.dpfht.android.demomovieflow.framework.data.datasource.remote.rest.RestService
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getPopularMovies(page: Int): Flow<Result<MovieDomain>> = flow {
    when (val result = safeApiCall(Dispatchers.IO) { restService.getPopularMovies(page) }) {
      is Success -> {
        val list = result.value.toDomain()

        emit(Success(list))
      }
      is ErrorResult -> {
        emit(result)
      }
    }
  }

  override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieEntity>> = flow {
    when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieDetails(movieId) }) {
      is Success -> {
        val entity = result.value.toDomain()

        emit(Success(entity))
      }
      is ErrorResult -> {
        emit(result)
      }
    }
  }

  override suspend fun searchMovie(query: String, page: Int): Flow<Result<MovieDomain>> = flow {
    when (val result = safeApiCall(Dispatchers.IO) { restService.searchMovie(query, page) }) {
      is Success -> {
        val list = result.value.toDomain()

        emit(Success(list))
      }
      is ErrorResult -> {
        emit(result)
      }
    }
  }

  //--

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
      try {
        Success(apiCall.invoke())
      } catch (t: Throwable) {
        when (t) {
          is IOException -> ErrorResult(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            val errorResponse = convertErrorBody(t)

            ErrorResult(errorResponse?.statusMessage ?: "")
          }
          else -> {
            ErrorResult(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
