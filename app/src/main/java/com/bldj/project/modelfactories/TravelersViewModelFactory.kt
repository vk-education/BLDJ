package com.bldj.project.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bldj.project.viewmodels.TravelersViewModel
import java.lang.IllegalArgumentException

class TravelersViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        TravelersViewModel::class.java -> TravelersViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}