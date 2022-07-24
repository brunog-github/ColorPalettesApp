package com.example.colorpalettesapp.presentation.screen.create

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(
    navController: NavHostController
) {
    
    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CreateTopBar { navController.popBackStack() }
        },
        drawerContent = {},
        content = {
            CreateContent(onSubmittedClicked = { Log.d("CreateScreen", it) })
        }
    )
}