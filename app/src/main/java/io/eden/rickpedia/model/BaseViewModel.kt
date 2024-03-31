package io.eden.rickpedia.model

import androidx.lifecycle.ViewModel

abstract class BaseViewModel() : ViewModel() {

    abstract fun resetState()
}