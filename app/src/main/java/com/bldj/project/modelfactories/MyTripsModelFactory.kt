package com.bldj.project.modelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bldj.project.viewmodels.MyTripsViewModel
import java.lang.IllegalArgumentException

class MyTripsModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        MyTripsViewModel::class.java -> MyTripsViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered")
    } as T
}