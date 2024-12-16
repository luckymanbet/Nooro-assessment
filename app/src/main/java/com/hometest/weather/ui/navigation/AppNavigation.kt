package com.hometest.weather.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hometest.weather.ui.detail.DetailScreen
import com.hometest.weather.ui.detail.data.DetailState
import com.hometest.weather.ui.home.HomeScreen
import com.hometest.weather.ui.home.HomeViewModel

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val detailState = viewModel.detailState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier = modifier,
    ) {
        addHomeScreen(padding, navController, viewModel)
        addDetailScreen(padding, detailState)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeScreen(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    composable<Routes.Home>()
    {
        HomeScreen(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            onNavigateToDetail = {
                navController.navigate(Routes.Detail)
            })
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDetailScreen(
    padding: PaddingValues,
    state: DetailState?
) {
    composable<Routes.Detail>()
    {
        DetailScreen(
            modifier = Modifier.padding(padding),
            state = state,
        )
    }
}