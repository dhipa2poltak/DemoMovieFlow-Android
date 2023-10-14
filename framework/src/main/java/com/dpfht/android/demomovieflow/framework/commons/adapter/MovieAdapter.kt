package com.dpfht.android.demomovieflow.framework.commons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.framework.R
import com.dpfht.android.demomovieflow.framework.commons.adapter.MovieAdapter.ViewHolder
import com.dpfht.android.demomovieflow.framework.databinding.RowMovieBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieAdapter @Inject constructor(

): PagingDataAdapter<MovieEntity, ViewHolder>(DataDifferentiator) {

  var onClickMovieCallback: ((MovieEntity) -> Unit)? = null

  class ViewHolder(val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.tvTitleMovie.text = ""
    holder.binding.tvOverviewMovie.text = ""
    holder.binding.ivMovie.setImageResource(R.drawable.no_image)
    holder.binding.root.setOnClickListener(null)

    val movieEntity = getItem(position)
    movieEntity?.let {
      holder.binding.tvTitleMovie.text = movieEntity.title
      holder.binding.tvOverviewMovie.text = movieEntity.overview

      if (movieEntity.imageUrl.isNotEmpty()) {
        Picasso.get().load(movieEntity.imageUrl)
          .error(R.drawable.no_image)
          .placeholder(R.drawable.no_image)
          .into(holder.binding.ivMovie)
      }

      holder.binding.root.setOnClickListener {
        onClickMovieCallback?.let { it(movieEntity) }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  object DataDifferentiator : DiffUtil.ItemCallback<MovieEntity>() {

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
      return oldItem == newItem
    }
  }
}
