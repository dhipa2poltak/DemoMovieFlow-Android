package com.dpfht.android.demomovieflow.domain.entity

sealed class VoidResult {
  object Success: VoidResult()
  data class Error(val message: String): VoidResult()
}
