package com.dpfht.android.demomovieflow.framework.commons.model

import com.dpfht.demomovieflow.data.model.Genre
import com.dpfht.demomovieflow.data.model.toDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieArgModelTest {

  @Test
  fun `ensure the converting from MovieArgModel to domain is success`() = runTest {
    val movieId = 101
    val movieTitle = "movieTitle"
    val movieOverview = "movieOverview"
    val movieImageUrl = "movieImageUrl"
    val genres = listOf<Genre>()

    val movieEntity = MovieEntity(movieId, movieTitle, movieOverview, movieImageUrl, genres.map { it.toDomain() })

    val argModel = MovieArgModel(
      id = movieId,
      title = movieTitle,
      overview = movieOverview,
      imageUrl = movieImageUrl,
      genres = genres
    )

    val actual = argModel.toDomain()

    assertTrue(movieEntity == actual)
  }

  @Test
  fun `ensure the converting from MovieArgModel which has null properties to domain is success`() = runTest {
    val movieId: Int? = null
    val movieTitle: String? = null
    val movieOverview: String? = null
    val movieImageUrl: String? = null
    val genres: List<Genre>? = null

    val movieEntity = MovieEntity(0, "", "", "", listOf())

    val argModel = MovieArgModel(
      id = movieId,
      title = movieTitle,
      overview = movieOverview,
      imageUrl = movieImageUrl,
      genres = genres
    )

    val actual = argModel.toDomain()

    assertTrue(movieEntity == actual)
  }
}
