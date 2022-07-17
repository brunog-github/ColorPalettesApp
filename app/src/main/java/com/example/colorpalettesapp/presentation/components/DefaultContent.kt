package com.example.colorpalettesapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.colorpalettesapp.domain.model.ColorPalette

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
                onClick = {}
            )
        }
    }
}