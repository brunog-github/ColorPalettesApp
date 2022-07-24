package com.example.colorpalettesapp.presentation.screen.create

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.presentation.components.ColorPicker

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(
    navController: NavHostController
) {
    
    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {},
        drawerContent = {},
        content = {
            ColorPicker(selectedColor = {})
        }
    )
}