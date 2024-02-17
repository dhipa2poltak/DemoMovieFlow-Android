package com.dpfht.android.demomovieflow.feature_splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.demomovieflow.framework.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

  private val _hasFinishedDelaying = MutableLiveData<Boolean>()
  val hasFinishedDelaying: LiveData<Boolean> = _hasFinishedDelaying

  fun start() {
    viewModelScope.launch {
      delay(Constants.DELAY_SPLASH)
      _hasFinishedDelaying.postValue(true)
    }
  }
}
