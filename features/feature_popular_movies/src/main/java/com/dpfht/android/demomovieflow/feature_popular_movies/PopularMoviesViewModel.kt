package com.dpfht.android.demomovieflow.feature_popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dpfht.android.demomovieflow.feature_popular_movies.paging.PopularMoviesDataSource
import com.dpfht.android.demomovieflow.framework.commons.adapter.MovieAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
  private val popularMoviesDataSource: PopularMoviesDataSource,
  val adapter: MovieAdapter
): ViewModel() {

  private val _isNoData = MutableLiveData<Boolean>()
  val isNoData: LiveData<Boolean> = _isNoData

  fun start() {
    if (adapter.itemCount > 0) {
      return
    }

    popularMoviesDataSource.rawNoData = _isNoData
    val pager = Pager(PagingConfig(pageSize = 20)) {
      popularMoviesDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        adapter.submitData(it)
      }
    }
  }
}
