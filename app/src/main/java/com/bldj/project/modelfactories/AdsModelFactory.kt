package com.bldj.project.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bldj.project.viewmodels.AdsViewModel
import java.lang.IllegalArgumentException

class AdsModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        AdsViewModel::class.java -> AdsViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}