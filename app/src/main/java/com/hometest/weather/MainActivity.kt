package com.hometest.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hometest.weather.ui.navigation.AppNavigation
import com.hometest.weather.ui.navigation.TopBar
import com.hometest.weather.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                val topBarState = rememberSaveable { (mutableStateOf(false)) }
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AnimatedVisibility(
                            visible = topBarState.value,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                        ) {
                            TopBar(
                                Modifier,
                                navController,
                                bottomBarState
                            )
                        }
                    },
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier,
                        navController = navController,
                        padding = innerPadding,
                    )
                }
            }
        }
    }
}
