package com.dpfht.android.demomovieflow.feature_popular_movies.paging

import androidx.paging.PagingSource
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject

class PopularMoviesDataSource @Inject constructor(
  private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, MovieEntity>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<MovieEntity>()

      getPopularMoviesUseCase(currentLoadingPageKey).collect { result ->
        when (result) {
          is Result.Success -> {
            arrList.addAll(result.value.results)
          }
          is Result.ErrorResult -> {

          }
        }
      }

      val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

      return LoadResult.Page(
        data = arrList,
        prevKey = prevKey,
        nextKey = currentLoadingPageKey.plus(1)
      )
    } catch (e: Exception) {
      return LoadResult.Error(e)
    }
  }
}
