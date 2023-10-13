package com.dpfht.android.demomovieflow.feature_favorite_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.android.demomovieflow.domain.usecase.GetAllFavoriteMoviesUseCase
import com.dpfht.android.demomovieflow.feature_favorite_movies.adapter.FavoriteMoviesAdapter
import com.dpfht.android.demomovieflow.framework.commons.model.FavoriteMovieCacheModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
  private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
  private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
  private val cacheModels: ArrayList<FavoriteMovieCacheModel>,
  val adapter: FavoriteMoviesAdapter
): ViewModel() {

  private val _isShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = _isShowDialogLoading

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  private val _isNoData = MutableLiveData<Boolean>()
  val isNoData: LiveData<Boolean> = _isNoData

  var isFromDetails = false
  var isRemovingMovie = true
  var movieIdToRemove = -1

  init {
    adapter.cacheModels = cacheModels
    adapter.scope = viewModelScope
  }

  fun start() {
    if (isFromDetails) {
      if (isRemovingMovie && movieIdToRemove != -1) {
        deleteFavoriteMovie(movieIdToRemove)
      }

      isFromDetails = false
      return
    }

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
    _isNoData.postValue(cacheModels.isEmpty())
  }

  private fun onErrorGetAllFavoriteMovies(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
    _isNoData.postValue(cacheModels.isEmpty())
  }

  private fun deleteFavoriteMovie(movieId: Int) {
    _isShowDialogLoading.postValue(true)

    val cacheModel = cacheModels.first { it.favEntity.movieId == movieId }
    val movieEntity = cacheModel.movieEntity

    movieEntity?.let {
      viewModelScope.launch {
        deleteFavoriteMovieUseCase(movieEntity).collect { voidResult ->
          when (voidResult) {
            VoidResult.Success -> {
              onSuccessDeleteFavoriteMovie(movieId)
            }
            is VoidResult.Error -> {
              onErrorDeleteFavoriteMovie(voidResult.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessDeleteFavoriteMovie(movieId: Int) {
    _isShowDialogLoading.postValue(false)

    val cacheModel = cacheModels.first { it.favEntity.movieId == movieId }
    val position = cacheModels.indexOf(cacheModel)
    if (position != -1) {
      cacheModels.removeAt(position)
      adapter.notifyItemRemoved(position)
    }

    isRemovingMovie = false
    movieIdToRemove = -1
    _isNoData.postValue(cacheModels.isEmpty())
  }

  private fun onErrorDeleteFavoriteMovie(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")

    isRemovingMovie = false
    movieIdToRemove = -1
    _isNoData.postValue(cacheModels.isEmpty())
  }
}
