package com.example.colorpalettesapp.data.repository

import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.BackendlessDataSource
import com.example.colorpalettesapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backendless: BackendlessDataSource
): Repository {

    override suspend fun getColorPalettes(): List<ColorPalette> {
        return backendless.getColorPalettes()
    }
}