package com.example.colorpalettesapp.domain.repository

import com.example.colorpalettesapp.domain.model.ColorPalette

interface Repository {

    suspend fun getColorPalettes(): List<ColorPalette>
}