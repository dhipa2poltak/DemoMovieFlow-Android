package com.dpfht.android.demomovieflow.framework.commons.model

import androidx.annotation.Keep
import com.dpfht.demomovieflow.data.model.Genre
import com.dpfht.demomovieflow.data.model.toDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class MovieArgModel(
  val adult: Boolean? = false,

  @SerializedName("backdrop_path")
  @Expose
  val backdropPath: String? = "",

  @SerializedName("genre_ids")
  @Expose
  val genreIds: List<Int>? = listOf(),

  val id: Int? = -1,

  @SerializedName("original_language")
  @Expose
  val originalLanguage: String? = "",

  @SerializedName("original_title")
  @Expose
  val originalTitle: String? = "",

  val overview: String? = "",
  val popularity: Float? = 0.0f,

  @SerializedName("poster_path")
  @Expose
  val posterPath: String? = "",

  @SerializedName("release_date")
  @Expose
  val releaseDate: String? = "",

  val title: String? = "",
  val video: Boolean? = false,

  @SerializedName("vote_average")
  @Expose
  val voteAverage: Float? = 0.0f,

  @SerializedName("vote_count")
  @Expose
  val voteCount: Int? = -1,

  @SerializedName("image_url")
  @Expose
  val imageUrl: String? = "",

  @SerializedName("genres")
  @Expose
  val genres: List<Genre>? = listOf(),
)

fun MovieArgModel.toDomain(): MovieEntity {
  return MovieEntity(
    id = this.id ?: 0,
    title = this.title ?: "",
    overview = this.overview ?: "",
    imageUrl = this.imageUrl ?: "",
    genres = this.genres?.map { it.toDomain() } ?: listOf()
  )
}
