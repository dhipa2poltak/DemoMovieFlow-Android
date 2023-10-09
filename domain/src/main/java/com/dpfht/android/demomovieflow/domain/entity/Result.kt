package com.dpfht.android.demomovieflow.domain.entity

sealed class Result<out T> {
  data class Success<out T>(val value: T): Result<T>()
  data class ErrorResult(val message: String): Result<Nothing>()
}
