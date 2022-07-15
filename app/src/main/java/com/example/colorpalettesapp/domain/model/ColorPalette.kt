package com.example.colorpalettesapp.domain.model

data class ColorPalette(
    var objectId: String? = null,
    var approved: Boolean = false,
    var colors: String? = null,
    var totalLikes: Int? = null
)
