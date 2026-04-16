package weather.app.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import weather.app.ui.search.GlobalSearchBar
import weather.app.ui.forecast.ForecastScreen
import weather.app.ui.forecast.ForecastViewModel
import weather.app.ui.forecast.ForecastViewModelFactory
import weather.app.ui.search.SearchViewModel
import weather.app.ui.search.SearchViewModelFactory

@Composable
fun AppNavHost(
    searchFactory: SearchViewModelFactory,
    forecastFactory: ForecastViewModelFactory,
    navController: NavHostController = rememberNavController()
) {
    val searchViewModel: SearchViewModel = viewModel(factory = searchFactory)
    val uiState by searchViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.lastLocation) {
        if (uiState.lastLocation != null && navController.currentDestination?.route == "search") {
            navController.navigate("forecast/${uiState.lastLocation!!.url}") {
                popUpTo("search") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            GlobalSearchBar(
                viewModel = searchViewModel,
                onLocationSelected = { location ->
                    navController.navigate("forecast/${location.url}")
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "search",
            modifier = Modifier.padding(padding)
        ) {
            composable("search") {
            // noop
            }

            composable(
                "forecast/{url}",
                arguments = listOf(navArgument("url") { type = NavType.StringType })
            ) { entry ->
                val url = entry.arguments?.getString("url")!!
                val forecastViewModel: ForecastViewModel = viewModel(factory = forecastFactory)

                ForecastScreen(
                    viewModel = forecastViewModel,
                    locationUrl = url
                )
            }
        }
    }
}

