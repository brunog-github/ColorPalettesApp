package com.example.colorpalettesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.backendless.Backendless
import com.example.colorpalettesapp.navigation.SetupNavGraph
import com.example.colorpalettesapp.ui.theme.ColorPalettesAppTheme
import com.example.colorpalettesapp.util.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)

        setContent {
            ColorPalettesAppTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}