package com.dpfht.demomovieflow.data.model

import com.dpfht.demomovieflow.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieResponseTest {

  @Test
  fun `ensure converting MovieResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("MovieResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<MovieResponse>() {}.type
    val response = Gson().fromJson<MovieResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.results.size == 20)
  }
}
