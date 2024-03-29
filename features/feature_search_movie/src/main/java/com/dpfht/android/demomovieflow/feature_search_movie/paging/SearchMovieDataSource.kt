package com.dpfht.android.demomovieflow.feature_search_movie.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.Result.Error
import com.dpfht.demomovieflow.domain.usecase.SearchMovieUseCase
import javax.inject.Inject

class SearchMovieDataSource @Inject constructor(
  private val searchMovieUseCase: SearchMovieUseCase
) : PagingSource<Int, MovieEntity>() {

  lateinit var query: String
  lateinit var rawNoData: MutableLiveData<Boolean>
  lateinit var rawErrorMessage: MutableLiveData<String>

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
          is Error -> {
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
      rawErrorMessage.value = e.message
      rawErrorMessage.postValue("")
      return LoadResult.Error(e)
    }
  }
}
