package com.dpfht.android.demomovieflow.feature_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.Result.Error
import com.dpfht.demomovieflow.domain.entity.VoidResult
import com.dpfht.demomovieflow.domain.entity.VoidResult.Success
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomovieflow.domain.usecase.AddFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.GetFavoriteMovieUseCase
import com.dpfht.demomovieflow.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
  private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
  private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
): ViewModel() {

  private val _isShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = _isShowDialogLoading

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  private val _titleData = MutableLiveData<String>()
  val titleData: LiveData<String> = _titleData

  private val _overviewData = MutableLiveData<String>()
  val overviewData: LiveData<String> = _overviewData

  private val _imageUrlData = MutableLiveData<String>()
  val imageUrlData: LiveData<String> = _imageUrlData

  private val _genres = MutableLiveData<String>()
  val genres: LiveData<String> = _genres

  private val _isFavoriteData = MutableLiveData<Boolean>()
  val isFavoriteData: LiveData<Boolean> = _isFavoriteData

  var movieId = -1

  var movieEntity: MovieEntity? = null
  var isForResult = false

  fun start() {
    if (movieId == -1) {
      return
    }

    if (movieEntity == null) {
      _isShowDialogLoading.postValue(true)
      viewModelScope.launch {
        getMovieDetailsUseCase(movieId).collect { result ->
          when (result) {
            is Result.Success -> {
              movieEntity = result.value
              onSuccessGetMovieDetails(result.value)
              checkIsFavorite(result.value)
            }

            is Error -> {
              onErrorGetMovieDetails(result.message)
            }
          }
        }
      }
    } else {
      movieEntity?.let {
        onSuccessGetMovieDetails(it)
        checkIsFavorite(it)
      }
    }
  }

  private fun onSuccessGetMovieDetails(movieEntity: MovieEntity) {
    movieId = movieEntity.id

    val title = movieEntity.title
    val overview = movieEntity.overview
    val imageUrl = movieEntity.imageUrl

    _titleData.postValue(title)
    _overviewData.postValue(overview)
    _imageUrlData.postValue(imageUrl)

    var strGenres = ""
    for (genre in movieEntity.genres) {
      if (strGenres.isEmpty()) {
        strGenres = genre.name
      } else {
        strGenres += ", ${genre.name}"
      }
    }

    _genres.postValue(strGenres)
  }

  private fun onErrorGetMovieDetails(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
  }

  private fun checkIsFavorite(movieEntity: MovieEntity) {
    viewModelScope.launch {
      getFavoriteMovieUseCase(movieEntity.id).collect { result ->
        when (result) {
          is Result.Success -> {
            onSuccessCheckIsFavorite(result.value)
          }
          is Error -> {
            onErrorCheckIsFavorite(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessCheckIsFavorite(entity: FavoriteMovieDBEntity?) {
    _isShowDialogLoading.postValue(false)
    _isFavoriteData.postValue(entity != null)
  }

  private fun onErrorCheckIsFavorite(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
  }

  fun addFavoriteMovie() {
    if (isForResult) {
      _isFavoriteData.postValue(true)
      return
    }

    _isShowDialogLoading.postValue(true)

    viewModelScope.launch {
      movieEntity?.let {
        addFavoriteMovieUseCase(it).collect { result ->
          when (result) {
            is Result.Success -> {
              onSuccessAddFavoriteMovie(result.value)
            }
            is Error -> {
              onErrorAddFavoriteMovie(result.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessAddFavoriteMovie(entity: FavoriteMovieDBEntity) {
    _isShowDialogLoading.postValue(false)
    _isFavoriteData.postValue(entity.id > 0)
  }

  private fun onErrorAddFavoriteMovie(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
  }

  fun deleteFavoriteMovie() {
    if (isForResult) {
      _isFavoriteData.postValue(false)
      return
    }

    _isShowDialogLoading.postValue(true)

    viewModelScope.launch {
      movieEntity?.let {
        deleteFavoriteMovieUseCase(it).collect { voidResult ->
          when (voidResult) {
            Success -> {
              onSuccessDeleteFavoriteMovie()
            }
            is VoidResult.Error -> {
              onErrorDeleteFavoriteMovie(voidResult.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessDeleteFavoriteMovie() {
    _isShowDialogLoading.postValue(false)
    _isFavoriteData.postValue(false)
  }

  private fun onErrorDeleteFavoriteMovie(message: String) {
    _isShowDialogLoading.postValue(false)
    _modalMessage.value = message
    _modalMessage.postValue("")
  }
}
