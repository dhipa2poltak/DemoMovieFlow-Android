package com.dpfht.android.demomovieflow.data.model

import androidx.annotation.Keep
import com.dpfht.android.demomovieflow.domain.entity.GenreEntity

@Keep
data class Genre(
    val id: Int? = -1,
    val name: String? = ""
)

fun Genre.toDomain(): GenreEntity {
    return GenreEntity(
        id = this.id ?: -1,
        name = this.name ?: ""
    )
}
