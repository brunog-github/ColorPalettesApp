package com.example.colorpalettesapp.presentation.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.colorpalettesapp.presentation.screen.hexToColor
import com.example.colorpalettesapp.ui.theme.InfoGreen
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    selectedColor: (String?) -> Unit,
    background: String = if (isSystemInDarkTheme()) "000000" else "FFFFFF"
) {

    var openDialog by remember { mutableStateOf(false) }
    var colorIsSelected by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(background) }

    if (openDialog) {
        DisplayAlertDialog(
            onDismissClicked = { openDialog = false },
            onConfirmClicked = { colorHex ->
                openDialog = false
                backgroundColor = colorHex
                colorIsSelected = true
                selectedColor(colorHex)
            }
        )
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray.copy(alpha = ContentAlpha.disabled))
            .background(color = hexToColor(colorHex = backgroundColor))
            .clickable(enabled = !colorIsSelected) { openDialog = true },
        contentAlignment = Alignment.Center
    ) {

        if (colorIsSelected) {
            IconButton(
                onClick = {
                    backgroundColor = background
                    colorIsSelected = false
                    selectedColor(null)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Delete Icon",
                    tint = MaterialTheme.colors.surface
                )
            }
        } else {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Icon",
                tint = InfoGreen
            )
        }
    }
}

@Composable
fun DisplayAlertDialog(
    onDismissClicked: () -> Unit,
    onConfirmClicked: (String) -> Unit,
    background: String = if (isSystemInDarkTheme()) "000000" else "FFFFFF"
) {

    val controller = rememberColorPickerController()
    var selectedColorHex by remember { mutableStateOf(background) }

    AlertDialog(
        onDismissRequest = onDismissClicked,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                        .clip(CircleShape)
                        .background(hexToColor(colorHex = selectedColorHex))
                )
                Spacer(modifier = Modifier.width(15.dp))
                BasicTextField(
                    value = selectedColorHex,
                    onValueChange = {
                        if (it.length <= 8) {
                            selectedColorHex = it.uppercase()
                        }
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
                )
            }
        },
        text = {
            Column {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope ->
                        selectedColorHex = colorEnvelope.hexCode
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
                AlphaSlider(
                    modifier = Modifier.height(25.dp),
                    controller = controller,
                    borderRadius = 6.dp,
                    borderSize = 5.dp,
                    borderColor = Color.LightGray
                )
                Spacer(modifier = Modifier.height(15.dp))
                BrightnessSlider(
                    modifier = Modifier.height(25.dp),
                    controller = controller,
                    borderRadius = 6.dp,
                    borderSize = 5.dp,
                    borderColor = Color.LightGray
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        },
        confirmButton = {
            Button(onClick = { onConfirmClicked(selectedColorHex) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissClicked) {
                Text(text = "Cancel")
            }
        }
    )

}