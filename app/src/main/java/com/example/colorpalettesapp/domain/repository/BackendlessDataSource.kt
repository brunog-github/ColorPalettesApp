package com.example.colorpalettesapp.domain.repository

import com.example.colorpalettesapp.domain.model.ColorPalette

interface BackendlessDataSource {

    suspend fun getColorPalettes(): List<ColorPalette>
}