package com.example.colorpalettesapp.presentation.screen.saved

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var savedPalettes by mutableStateOf<List<ColorPalette>>(emptyList())
        private set

    init {
        getSavedPalettes()
        observeSavedColorPalettes()
    }

    private fun getSavedPalettes() {
        viewModelScope.launch(Dispatchers.IO) {
            savedPalettes = repository.getSavedPalettes(
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )
        }
    }

    private fun observeSavedColorPalettes() {
        val userObjectId = Backendless.UserService.CurrentUser().objectId

        viewModelScope.launch(Dispatchers.IO) {
            repository.observeSavedPalettes(userObjectId).collect {
                getSavedPalettes()
            }
        }
    }
}