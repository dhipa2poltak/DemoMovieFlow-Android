package com.dpfht.android.demomovieflow.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigationServiceTest {

    private lateinit var navigationService: NavigationService
    private lateinit var navController: NavController

    private val movieId = 101
    private val movieEntity = MovieEntity(movieId, "movie title", "movie overview", "movie image", listOf())

    @Before
    fun setup() {
        navController = mock()
        navigationService = NavigationServiceImpl(navController)
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToMovieDetails method in navigationService`() {
        navigationService.navigateToMovieDetails(movieId, movieEntity, false)

        verify(navController).navigate(any<NavDeepLinkRequest>())
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToErrorMessage method in navigationService`() {
        val errorMessage = "error message"
        navigationService.navigateToErrorMessage(errorMessage)
        verify(navController).navigate(any<NavDeepLinkRequest>())
    }

    @Test
    fun `ensure navigateUp method is called in navController when calling navigateUp method in navigationService`() {
        navigationService.navigateUp()
        verify(navController).navigateUp()
    }
}
