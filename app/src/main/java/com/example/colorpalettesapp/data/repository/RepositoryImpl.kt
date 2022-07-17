package com.example.colorpalettesapp.data.repository

import com.backendless.rt.data.RelationStatus
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.BackendlessDataSource
import com.example.colorpalettesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backendless: BackendlessDataSource
): Repository {

    override suspend fun getColorPalettes(): List<ColorPalette> {
        return backendless.getColorPalettes()
    }

    override suspend fun getLikeCount(objectId: String): ColorPalette {
        return backendless.getLikeCount(objectId)
    }

    override suspend fun observeAddRelation(): Flow<RelationStatus?> {
        return backendless.observeAddRelation()
    }

    override suspend fun observeDeleteRelation(): Flow<RelationStatus?> {
        return backendless.observeDeleteRelation()
    }

    override suspend fun observeApproval(): Flow<ColorPalette> {
        return backendless.observeApproval()
    }

    override suspend fun observeDeletePalettes(): Flow<ColorPalette> {
        return backendless.observeDeletePalettes()
    }
}