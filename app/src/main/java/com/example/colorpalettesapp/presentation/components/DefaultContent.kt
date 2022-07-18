package com.example.colorpalettesapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.navigation.Screen
import com.example.colorpalettesapp.util.Constants.COLOR_PALETTE_KEY

@Composable
fun DefaultContent(
    navController: NavHostController,
    colorPalette: List<ColorPalette>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        items(
            items = colorPalette,
            key = { it.objectId!! }
        ) { singleColorPalette ->
            PaletteHolder(
                colorPalette = singleColorPalette,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = COLOR_PALETTE_KEY,
                        value = singleColorPalette
                    )
                    navController.navigate(Screen.Details.route)
                }
            )
        }
    }
}