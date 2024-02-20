package com.dpfht.android.demomovieflow.feature_search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dpfht.android.demomovieflow.feature_search_movie.paging.SearchMovieDataSource
import com.dpfht.android.demomovieflow.framework.commons.adapter.MovieAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@HiltViewModel
class SearchMovieViewModel @Inject constructor(
  private val searchMovieDataSource: SearchMovieDataSource
): ViewModel() {

  private val _setAdapterData = MutableLiveData<Boolean>()
  val setAdapterData: LiveData<Boolean> = _setAdapterData

  private val _isNoData = MutableLiveData<Boolean>()
  val isNoData: LiveData<Boolean> = _isNoData

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  lateinit var adapter: MovieAdapter

  fun onSearchMovie(query: String) {
    adapter = MovieAdapter()
    _setAdapterData.value = true

    searchMovieDataSource.query = query
    searchMovieDataSource.rawNoData = _isNoData
    searchMovieDataSource.rawErrorMessage = _modalMessage
    val pager = Pager(PagingConfig(pageSize = 20)) {
      searchMovieDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        adapter.submitData(it)
      }
    }
  }
}
