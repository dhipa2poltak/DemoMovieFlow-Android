package com.dpfht.android.demomovieflow.feature_popular_movies.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.Result.Error
import com.dpfht.demomovieflow.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject

class PopularMoviesDataSource @Inject constructor(
  private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, MovieEntity>() {

  lateinit var rawNoData: MutableLiveData<Boolean>
  lateinit var rawErrorMessage: MutableLiveData<String>

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<MovieEntity>()
      var prevKey: Int? = null
      var nextKey: Int? = null

      getPopularMoviesUseCase(currentLoadingPageKey).collect { result ->
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
