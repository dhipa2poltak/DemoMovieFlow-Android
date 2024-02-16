package com.dpfht.demomovieflow.domain.usecase

import com.dpfht.demomovieflow.domain.repository.AppRepository
import org.mockito.Mock

open class BaseUseCaseTest {

  @Mock
  protected lateinit var appRepository: AppRepository
}
