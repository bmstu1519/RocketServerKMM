package org.rocketserverkmm.project.di.modules

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.rocketserverkmm.project.presentation.viewmodels.AppBootstrapViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchDetailsViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LoginViewModel
import org.rocketserverkmm.project.presentation.viewmodels.SettingsViewModel

val viewModelsModule = module {
    viewModelOf(::LaunchListViewModel)
    viewModelOf(::LaunchDetailsViewModel)

    viewModel {
        AppBootstrapViewModel(
            getAppBootstrapUseCase = get(),
            userConfigHolder = get()
        ).apply {
            initialize()
        }
    }

    viewModel {
        LoginViewModel(
            getLoginUseCase = get(),
            userConfigHolder = get()
        )
    }

    viewModel {
        SettingsViewModel(
            getSettingsUseCase = get(),
            userConfigHolder = get()
        )
    }
}