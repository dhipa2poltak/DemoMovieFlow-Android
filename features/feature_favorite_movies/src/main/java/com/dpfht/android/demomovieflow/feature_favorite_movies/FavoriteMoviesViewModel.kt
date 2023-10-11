package com.dpfht.android.demomovieflow.feature_favorite_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.usecase.GetAllFavoriteMoviesUseCase
import com.dpfht.android.demomovieflow.feature_favorite_movies.adapter.FavoriteMoviesAdapter
import com.dpfht.android.demomovieflow.framework.commons.model.FavoriteMovieCacheModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
  private val cacheModels: ArrayList<FavoriteMovieCacheModel>,
  private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
  val adapter: FavoriteMoviesAdapter
): ViewModel() {

  private val _isShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = _isShowDialogLoading

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  init {
    adapter.cacheModels = cacheModels
    adapter.scope = viewModelScope
  }

  fun start() {
    _isShowDialogLoading.postValue(true)

    viewModelScope.launch {
      getAllFavoriteMoviesUseCase().collect { result ->
        when (result) {
          is Result.Success -> {
            onSuccessGetAllFavoriteMovies(result.value)
          }
          is Result.ErrorResult -> {
            onErrorGetAllFavoriteMovies(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessGetAllFavoriteMovies(list: List<FavoriteMovieDBEntity>) {
    _isShowDialogLoading.postValue(false)
    cacheModels.clear()
    adapter.notifyDataSetChanged()
    cacheModels.addAll(list.map { FavoriteMovieCacheModel(it, null) })
    adapter.notifyDataSetChanged()
  }

  private fun onErrorGetAllFavoriteMovies(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
  }
}
