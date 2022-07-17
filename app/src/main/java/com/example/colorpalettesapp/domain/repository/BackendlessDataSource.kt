package com.example.colorpalettesapp.domain.repository

import com.backendless.rt.data.RelationStatus
import com.example.colorpalettesapp.domain.model.ColorPalette
import kotlinx.coroutines.flow.Flow

interface BackendlessDataSource {

    suspend fun getColorPalettes(): List<ColorPalette>

    suspend fun getLikeCount(objectId: String): ColorPalette

    suspend fun observeAddRelation(): Flow<RelationStatus?>

    suspend fun observeDeleteRelation(): Flow<RelationStatus?>

    suspend fun observeApprovedPalettes(ownerId: String): Flow<ColorPalette>

    suspend fun observeNotApprovedPalettes(ownerId: String): Flow<ColorPalette>

    suspend fun observeDeletePalettes(): Flow<ColorPalette>
}