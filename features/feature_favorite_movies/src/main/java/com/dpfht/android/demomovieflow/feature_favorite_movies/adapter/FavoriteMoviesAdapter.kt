package com.dpfht.android.demomovieflow.feature_favorite_movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.android.demomovieflow.feature_favorite_movies.adapter.FavoriteMoviesAdapter.ViewHolder
import com.dpfht.android.demomovieflow.framework.databinding.RowMovieBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMoviesAdapter @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): RecyclerView.Adapter<ViewHolder>() {

  lateinit var favoriteEntities: ArrayList<FavoriteMovieDBEntity>
  lateinit var scope: CoroutineScope

  var onClickMovieCallback: ((MovieEntity) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val favEntity = favoriteEntities[position]

    scope.launch {
      getMovieDetailsUseCase(favEntity.movieId).collect { result ->
        when (result) {
          is Result.Success -> {
            onSuccessGetMovieDetails(holder.binding, result.value)
          }
          is Result.ErrorResult -> {
            onErrorGetMovieDetails(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessGetMovieDetails(binding: RowMovieBinding, movieEntity: MovieEntity) {
    binding.tvTitleMovie.text = ""
    binding.tvOverviewMovie.text = ""
    binding.ivMovie.setImageURI(null)
    binding.root.setOnClickListener(null)

    binding.tvTitleMovie.text = movieEntity.title
    binding.tvOverviewMovie.text = movieEntity.overview

    if (movieEntity.imageUrl.isNotEmpty()) {
      Picasso.get().load(movieEntity.imageUrl).into(binding.ivMovie)
    }

    binding.root.setOnClickListener {
      onClickMovieCallback?.let { it(movieEntity) }
    }
  }

  private fun onErrorGetMovieDetails(message: String) {}

  override fun getItemCount(): Int {
    return favoriteEntities.size
  }

  class ViewHolder(val binding: RowMovieBinding): RecyclerView.ViewHolder(binding.root)
}
