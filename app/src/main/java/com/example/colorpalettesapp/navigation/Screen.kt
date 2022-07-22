package com.example.colorpalettesapp.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login")
    object Home: Screen("home")
    object Details: Screen("details/{showFab}") {
        fun passShowFab(showFab: Boolean) = "details/$showFab"
    }
    object Saved: Screen("saved")
    object Submitted: Screen("submitted")
    object Create: Screen("create")
}