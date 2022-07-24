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

    override suspend fun checkSavedPalette(
        paletteObjectId: String,
        userObjectId: String
    ): List<ColorPalette> {
        return backendless.checkSavedPalette(paletteObjectId, userObjectId)
    }

    override suspend fun saveColorPalette(paletteObjectId: String, userObjectId: String): Int {
        return backendless.saveColorPalette(paletteObjectId, userObjectId)
    }

    override suspend fun removeColorPalette(paletteObjectId: String, userObjectId: String): Int {
        return backendless.removeColorPalette(paletteObjectId, userObjectId)
    }

    override suspend fun addLike(paletteObjectId: String, userObjectId: String): Int? {
        return backendless.addLike(paletteObjectId, userObjectId)
    }

    override suspend fun removeLike(paletteObjectId: String, userObjectId: String): Int? {
        return backendless.removeLike(paletteObjectId, userObjectId)
    }

    override suspend fun getSavedPalettes(userObjectId: String): List<ColorPalette> {
        return backendless.getSavedPalettes(userObjectId)
    }

    override suspend fun observeSavedPalettes(userObjectId: String): Flow<RelationStatus?> {
        return backendless.observeSavedPalettes(userObjectId)
    }

    override suspend fun getSubmittedPalettes(userObjectId: String): List<ColorPalette> {
        return backendless.getSubmittedPalettes(userObjectId)
    }

    override suspend fun observeSubmittedPalettes(userObjectId: String): Flow<ColorPalette> {
        return backendless.observeSubmittedPalettes(userObjectId)
    }

    override suspend fun submitColorPalette(colorPalette: ColorPalette): ColorPalette {
        return backendless.submitColorPalette(colorPalette)
    }
}