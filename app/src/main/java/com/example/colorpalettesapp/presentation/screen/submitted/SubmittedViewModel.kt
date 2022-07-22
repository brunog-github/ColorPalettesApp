package com.example.colorpalettesapp.presentation.screen.submitted

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.colorpalettesapp.domain.model.ColorPalette
import com.example.colorpalettesapp.domain.repository.Repository
import com.example.colorpalettesapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubmittedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _submittedPalettes = mutableStateListOf<ColorPalette>()
    val submittedPalettes: List<ColorPalette> get() = _submittedPalettes

    var requestState by mutableStateOf<RequestState>(RequestState.Success)
        private set

}