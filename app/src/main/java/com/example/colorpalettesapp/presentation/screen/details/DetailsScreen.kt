package com.example.colorpalettesapp.presentation.screen.details

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.ui.theme.Gray700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    colorPalette: ColorPalette
) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailsTopBar(
                isSaved = false,
                onBackClicked = { },
                onSaveClicked = {}
            )
        },
        content = {
            DetailsContent(
                colorPalette = colorPalette,
                onColorClicked = {}
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                backgroundColor = Gray700,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Heart Icon",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "${colorPalette.totalLikes ?: "0"}",
                        color = Color.White
                    )
                },
                onClick = {}
            )
        }
    )
}