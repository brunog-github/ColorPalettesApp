package com.example.colorpalettesapp.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.rt.data.RelationStatus
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var colorPalettes = mutableStateListOf<ColorPalette>()
        private set

    init {
        getColorPalettes()
        observeAddRelation()
        observeDeleteRelation()
    }

    private fun getColorPalettes() {
        viewModelScope.launch(Dispatchers.IO) {
            colorPalettes.addAll(repository.getColorPalettes())
        }
    }

    private fun observeAddRelation() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeAddRelation().collect { addRelation ->
                updateNumberOfLikes(relationStatus = addRelation)
            }
        }
    }

    private fun observeDeleteRelation() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeDeleteRelation().collect { deleteRelation ->
                updateNumberOfLikes(relationStatus = deleteRelation)
            }
        }
    }

    private suspend fun updateNumberOfLikes(relationStatus: RelationStatus?) {
        val observedPalette = relationStatus?.parentObjectId?.let { repository.getLikeCount(it) }

        var position = 0
        var palette = ColorPalette()
        colorPalettes.forEachIndexed { index, colorPalette ->
            if (colorPalette.objectId == relationStatus?.parentObjectId) {
                position = index
                palette = colorPalette
            }
        }
        colorPalettes.set(
            index = position,
            element = palette.copy(totalLikes = observedPalette?.totalLikes)
        )
    }

}