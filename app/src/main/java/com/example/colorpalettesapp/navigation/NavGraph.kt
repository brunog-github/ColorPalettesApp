package com.example.colorpalettesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.colorpalettesapp.presentation.screen.home.HomeScreen
import com.example.colorpalettesapp.presentation.screen.login.LoginScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) { LoginScreen(navController = navController) }
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(route = Screen.Details.route) {}
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submitted.route) {}
        composable(route = Screen.Create.route) {}
    }
}