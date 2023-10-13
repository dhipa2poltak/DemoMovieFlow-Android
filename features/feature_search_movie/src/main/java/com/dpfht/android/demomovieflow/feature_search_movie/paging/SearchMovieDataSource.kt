package com.dpfht.android.demomovieflow.feature_search_movie.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.usecase.SearchMovieUseCase
import javax.inject.Inject

class SearchMovieDataSource @Inject constructor(
  private val searchMovieUseCase: SearchMovieUseCase
) : PagingSource<Int, MovieEntity>() {

  lateinit var query: String
  lateinit var rawNoData: MutableLiveData<Boolean>

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<MovieEntity>()
      var prevKey: Int? = null
      var nextKey: Int? = null

      searchMovieUseCase(query, currentLoadingPageKey).collect { result ->
        when (result) {
          is Result.Success -> {
            arrList.addAll(result.value.results)
            prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            nextKey = if (result.value.results.isEmpty()) null else currentLoadingPageKey + 1
          }
          is Result.ErrorResult -> {
            throw Exception(result.message)
          }
        }
      }

      rawNoData.postValue(currentLoadingPageKey == 1 && arrList.isEmpty())

      return LoadResult.Page(
        data = arrList,
        prevKey = prevKey,
        nextKey = nextKey
      )
    } catch (e: Exception) {
      return LoadResult.Error(e)
    }
  }
}
