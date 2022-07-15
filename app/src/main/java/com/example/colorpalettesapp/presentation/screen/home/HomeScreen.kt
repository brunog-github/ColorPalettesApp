package com.example.colorpalettesapp.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.presentation.components.NavigationDrawer
import com.example.colorpalettesapp.presentation.components.PaletteHolder

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopBar(scaffoldState = scaffoldState) },
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                scaffoldState = scaffoldState,
                logoutFailed = {}
            )
        },
        content = {
            PaletteHolder(
                colorPalette = ColorPalette(
                    objectId = null,
                    approved = true,
                    colors = "#F1DDBF,#0093AB,#00AFC1",
                    totalLikes = 120
                ),
                onClick = {}
            )
        }
    )
}