package com.bignerdranch.android.medicaldosing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "MedicalDosingModel"
const val CURRENT_AGE_KEY = "CURRENT_AGE_KEY"
const val CURRENT_WEIGHT_KEY = "CURRENT_WEIGHT_KEY"

class MedicalDosingModel (private val savedStateHandle: SavedStateHandle): ViewModel() {
}