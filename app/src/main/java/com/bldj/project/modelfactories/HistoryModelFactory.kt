package com.bldj.project.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bldj.project.viewmodels.HistoryViewModel
import java.lang.IllegalArgumentException

class HistoryModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        HistoryViewModel::class.java -> HistoryViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}