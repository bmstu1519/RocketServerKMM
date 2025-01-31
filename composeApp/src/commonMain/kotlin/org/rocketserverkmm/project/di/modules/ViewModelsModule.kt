package org.rocketserverkmm.project.di.modules

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.rocketserverkmm.project.domain.repositories.AppBootstrapRepository
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.repositories.SettingsRepository
import org.rocketserverkmm.project.domain.usecases.GetAppBootstrapUseCase
import org.rocketserverkmm.project.domain.usecases.GetLaunchDetailsUseCase
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase
import org.rocketserverkmm.project.domain.usecases.GetLoginUseCase
import org.rocketserverkmm.project.domain.usecases.GetSettingsUseCase
import org.rocketserverkmm.project.presentation.viewmodels.AppBootstrapViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchDetailsViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LoginViewModel
import org.rocketserverkmm.project.presentation.viewmodels.SettingsViewModel
import org.rocketserverkmm.project.data.repositories.AppBootstrapRepositoryImpl
import org.rocketserverkmm.project.data.repositories.KeyVaultRepositoryImpl
import org.rocketserverkmm.project.data.repositories.LaunchRepositoryImpl
import org.rocketserverkmm.project.data.repositories.SettingsRepositoryImpl
import org.rocketserverkmm.project.settings.local.UserConfigHolder

val viewModelsModule = module {
    singleOf(::LaunchRepositoryImpl).bind<LaunchRepository>()
    singleOf(::KeyVaultRepositoryImpl).bind<KeyVaultRepository>()
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()
    singleOf(::AppBootstrapRepositoryImpl).bind<AppBootstrapRepository>()

    viewModelOf(::LaunchListViewModel)
    viewModelOf(::LaunchDetailsViewModel)

    single {
        UserConfigHolder()
    }

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

    factory {
        val launchRepository: LaunchRepository = get()
        GetLaunchesUseCase(launchRepository)
    }

    factory {
        val launchRepository: LaunchRepository = get()
        val keyVaultRepository: KeyVaultRepository = get()
        GetLaunchDetailsUseCase(launchRepository, keyVaultRepository)
    }

    factory {
        val launchRepository: LaunchRepository = get()
        val keyVaultRepository: KeyVaultRepository = get()
        GetLoginUseCase(launchRepository, keyVaultRepository)
    }

    factory {
        val settingsRepository: SettingsRepository = get()
        GetSettingsUseCase(settingsRepository)
    }
    factory {
        val appBootstrapRepository: AppBootstrapRepository = get()
        GetAppBootstrapUseCase(appBootstrapRepository)
    }
}