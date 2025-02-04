package edu.vt.cs5254.multiquiz

import androidx.annotation.StringRes

data class ModelButton (
    @StringRes val textResId: Int,
    var isEnabled: Boolean = true,
    var isSelected: Boolean = false
)