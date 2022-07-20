package com.example.colorpalettesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.presentation.screen.details.DetailsScreen
import com.example.colorpalettesapp.presentation.screen.home.HomeScreen
import com.example.colorpalettesapp.presentation.screen.login.LoginScreen
import com.example.colorpalettesapp.presentation.screen.saved.SavedScreen
import com.example.colorpalettesapp.util.Constants.COLOR_PALETTE_KEY

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) { LoginScreen(navController = navController) }
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(route = Screen.Details.route) {
            val selectedPalette =
                navController.previousBackStackEntry?.savedStateHandle?.get<ColorPalette>(
                    key = COLOR_PALETTE_KEY
                )
            if (selectedPalette != null) {
                DetailsScreen(
                    navController = navController,
                    colorPalette = selectedPalette
                )
            }

        }
        composable(route = Screen.Saved.route) { SavedScreen(navController = navController) }
        composable(route = Screen.Submitted.route) {}
        composable(route = Screen.Create.route) {}
    }
}