package com.example.colorpalettesapp.presentation.screen.submitted

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.navigation.Screen
import com.example.colorpalettesapp.presentation.components.NavigationDrawer
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SubmittedScreen(
    navController: NavHostController,
    submittedViewModel: SubmittedViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val submittedPalettes = submittedViewModel.submittedPalettes
    val requestState = submittedViewModel.requestState

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SubmittedTopBar(
                submittedColors = submittedPalettes,
                requestState = requestState,
                onSubmitClicked = { navController.navigate(Screen.Create.route) },
                onMenuClicked = { scope.launch { scaffoldState.drawerState.open()  } }
            )
        },
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                scaffoldState = scaffoldState,
                logoutFailed = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Something went wrong",
                            "Ok"
                        )
                    }
                }
            )
        },
        content = {
            SubmittedContent(
                navController = navController,
                submittedColors = submittedPalettes,
                requestState = requestState,
                onSubmitClicked = { navController.navigate(Screen.Create.route) }
            )
        }
    )

}