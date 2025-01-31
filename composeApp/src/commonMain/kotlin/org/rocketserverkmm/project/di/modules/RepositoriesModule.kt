package org.rocketserverkmm.project.di.modules

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.rocketserverkmm.project.data.repositories.AppBootstrapRepositoryImpl
import org.rocketserverkmm.project.data.repositories.KeyVaultRepositoryImpl
import org.rocketserverkmm.project.data.repositories.LaunchRepositoryImpl
import org.rocketserverkmm.project.data.repositories.SettingsRepositoryImpl
import org.rocketserverkmm.project.domain.repositories.AppBootstrapRepository
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.repositories.SettingsRepository

val repositoriesModule = module {
    singleOf(::LaunchRepositoryImpl).bind<LaunchRepository>()
    singleOf(::KeyVaultRepositoryImpl).bind<KeyVaultRepository>()
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()
    singleOf(::AppBootstrapRepositoryImpl).bind<AppBootstrapRepository>()
}