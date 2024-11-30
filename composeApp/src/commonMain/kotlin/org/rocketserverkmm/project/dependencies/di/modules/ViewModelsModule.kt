package org.rocketserverkmm.project.dependencies.di.modules

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.usecases.GetLaunchDetailsUseCase
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase
import org.rocketserverkmm.project.domain.usecases.GetLoginUseCase
import org.rocketserverkmm.project.presentation.viewmodels.LaunchDetailsViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LaunchListViewModel
import org.rocketserverkmm.project.presentation.viewmodels.LoginViewModel
import org.rocketserverkmm.project.repositories.KeyVaultRepositoryImpl
import org.rocketserverkmm.project.repositories.LaunchRepositoryImpl

val viewModelsModule = module {
    singleOf(::LaunchRepositoryImpl).bind<LaunchRepository>()
    singleOf(::KeyVaultRepositoryImpl).bind<KeyVaultRepository>()

    viewModelOf(::LaunchListViewModel)
    viewModelOf(::LaunchDetailsViewModel)
    viewModelOf(::LoginViewModel)

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
}