package com.example.colorpalettesapp.presentation.screen.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.Repository
import com.example.colorpalettesapp.presentation.screen.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var isSaved by mutableStateOf(false)
        private set

    var selectedPalette by mutableStateOf(ColorPalette())
        private set

    private val _uiEvent = Channel<DetailsScreenUiEvent>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    fun updateSelectedPalette(colorPalette: ColorPalette) {
        checkSavedPalette(colorPalette.objectId!!)
        selectedPalette = colorPalette
    }

    private fun checkSavedPalette(objectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkSavedPalette(
                paletteObjectId = objectId,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )

            isSaved = result.any { it.objectId == objectId }
        }
    }

    fun saveOrRemoveColorPalette() {
        val userObjectId = Backendless.UserService.CurrentUser().objectId

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.saveColorPalette(
                    paletteObjectId = selectedPalette.objectId!!,
                    userObjectId = userObjectId
                )

                if (result == 0) {
                    repository.removeColorPalette(
                        paletteObjectId = selectedPalette.objectId!!,
                        userObjectId = userObjectId
                    )
                    _uiEvent.send(DetailsScreenUiEvent.RemoveSavedPalette)
                    isSaved = false
                } else if (result > 0) {
                    isSaved = true
                    _uiEvent.send(DetailsScreenUiEvent.SavePalette)
                }
            } catch (e: Exception) {
                _uiEvent.send(
                    DetailsScreenUiEvent.Error(
                        text = parseErrorMessage(message = e.message.toString())
                    )
                )
            }
        }
    }

    fun addOrRemoveLike() {
        val userObjectId = Backendless.UserService.CurrentUser().objectId

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.addLike(
                    paletteObjectId = selectedPalette.objectId!!,
                    userObjectId = userObjectId
                )

                if (result == 0) {
                    repository.removeLike(selectedPalette.objectId!!, userObjectId)
                    selectedPalette = selectedPalette.totalLikes?.minus(1).let {
                        selectedPalette.copy(totalLikes = it)
                    }
                    _uiEvent.send(DetailsScreenUiEvent.RemoveLike)

                } else if (result != null && result > 0) {
                    selectedPalette = selectedPalette.totalLikes?.plus(1).let {
                        selectedPalette.copy(totalLikes = it)
                    }
                    _uiEvent.send(DetailsScreenUiEvent.AddLike)
                }
            } catch (e: Exception) {
                _uiEvent.send(
                    DetailsScreenUiEvent.Error(
                        text = parseErrorMessage(message = e.message.toString())
                    )
                )
            }
        }
    }


    fun copyToClipBoard(context: Context, color: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Color", color)
        clipboardManager.setPrimaryClip(clipData)

        viewModelScope.launch {
            _uiEvent.send(
                DetailsScreenUiEvent.CopyToClipboard(color = color)
            )
        }
    }
}

sealed class DetailsScreenUiEvent(val message: String) {
    object AddLike: DetailsScreenUiEvent(message = "Liked!")
    object RemoveLike: DetailsScreenUiEvent(message = "Removed a like!")
    object SavePalette: DetailsScreenUiEvent(message = "Saved!")
    object RemoveSavedPalette: DetailsScreenUiEvent(message = "Removed from saved!")
    data class Error(val text: String): DetailsScreenUiEvent(message = text)
    data class CopyToClipboard(val color: String): DetailsScreenUiEvent(message = "$color Copied!")
}