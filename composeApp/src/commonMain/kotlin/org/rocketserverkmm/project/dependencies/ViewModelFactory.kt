package org.rocketserverkmm.project.dependencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.rocketserverkmm.project.presentation.viewmodels.LaunchDetailsViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LoginViewModel
import kotlin.reflect.KClass

class ViewModelFactory: ViewModelProvider.Factory, KoinComponent {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return when (modelClass) {
            LaunchListViewModel::class -> {
                get<LaunchListViewModel>() as T
            }
            LoginViewModel::class -> {
                get<LoginViewModel>() as T
            }
            LaunchDetailsViewModel::class -> {
                get<LaunchDetailsViewModel>() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}