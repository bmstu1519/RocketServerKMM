package org.rocketserverkmm.project.di.modules

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

val useCasesModule = module {
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