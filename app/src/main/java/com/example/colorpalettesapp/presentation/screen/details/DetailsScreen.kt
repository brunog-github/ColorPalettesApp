package com.example.colorpalettesapp.presentation.screen.details

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.ui.theme.Gray700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    colorPalette: ColorPalette,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val isSaved = detailsViewModel.isSaved
    val selectedPalette = detailsViewModel.selectedPalette

    val scaffoldState = rememberScaffoldState()
    
    LaunchedEffect(key1 = colorPalette) {
        detailsViewModel.updateSelectedPalette(colorPalette)
    }

    LaunchedEffect(key1 = Unit) {
        detailsViewModel.uiEvent.collect { uiEvent ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = uiEvent.message,
                actionLabel = "Ok"
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailsTopBar(
                isSaved = isSaved,
                onBackClicked = { navController.popBackStack() },
                onSaveClicked = { detailsViewModel.saveColorPalette() }
            )
        },
        content = {
            DetailsContent(
                colorPalette = selectedPalette,
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