package com.dpfht.android.demomovieflow.framework

object Constants {

  const val DELAY_SPLASH = 3000L

  const val BASE_URL = "https://api.themoviedb.org/"
  const val ACCESS_TOKEN = BuildConfig.ACCESS_TOKEN

  object FragmentArgsName {
    const val ARG_MOVIE_ID = "movie_id"
    const val ARG_MOVIE_MODEL = "movie_model"
    const val ARG_IS_FOR_RESULT = "is_for_result"
    const val ARG_IS_FAVORITE = "is_favorite"
    const val ARG_MESSAGE = "message"
  }

  object FragmentActionKeys {
    const val ACTION_KEY_FAVORITE = "action_key_favorite"
  }
}
