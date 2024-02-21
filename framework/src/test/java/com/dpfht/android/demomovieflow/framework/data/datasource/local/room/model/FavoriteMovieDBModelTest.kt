package com.dpfht.android.demomovieflow.framework.data.datasource.local.room.model

import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)

class FavoriteMovieDBModelTest {

  @Test
  fun `ensure the converting from FavoriteMovieDBModel to domain is success`() = runTest {
    val id = 1
    val movieId = 101
    val favMovieEntity = FavoriteMovieDBEntity(id.toLong(), movieId)

    val dbModel = FavoriteMovieDBModel(id.toLong(), movieId)
    val actual = dbModel.toDomain()

    assertTrue(favMovieEntity == actual)
  }
}
