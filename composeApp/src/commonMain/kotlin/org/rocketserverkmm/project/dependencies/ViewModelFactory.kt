package org.rocketserverkmm.project.dependencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class ViewModelFactory(
    private val dependencyProvider: DependencyProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return when {
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}